package com.dataspin.oboiservice.service;

import com.dataspin.oboiservice.controller.IncomeController;
import com.dataspin.oboiservice.controller.OutcomeController;
import com.dataspin.oboiservice.entity.*;
import com.dataspin.oboiservice.model.CodeIdAndPartyIdModel;
import com.dataspin.oboiservice.model.IncomeOutcomeModel;
import com.dataspin.oboiservice.model.NameModel;
import com.dataspin.oboiservice.model.WarehouseModel;
import com.dataspin.oboiservice.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Configuration
public class WarehouseService {

    private final AuthService authService;
    private final WarehouseRepository warehouseRepository;
    private final IncomeRepository incomeRepository;
    private final OboiCodeRepository oboiCodeRepository;
    private final OboiPartyRepository oboiPartyRepository;
    private final RegRepository regRepository;
    private final IncomeTypeRepository incomeTypeRepository;
    private final OutcomeTypeRepository outcomeTypeRepository;
    private final OutcomeRepository outcomeRepository;

    public ResponseEntity<Object> getAllProduct(HttpServletRequest request) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(warehouseRepository.getAllInfo(), HttpStatus.OK);
    }

    public ResponseEntity<Object> changeProductStatus(CodeIdAndPartyIdModel model, HttpServletRequest request) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        final Warehouse warehouse = warehouseRepository.findByOboiCode_IdAndOboiParty_Id(model.code, model.party);
        warehouse.setStatus(!warehouse.getStatus());
        warehouseRepository.save(warehouse);
        final Map<String, Object> map = new HashMap<>();
        map.put("details", "Status changed to '" + warehouse.getStatus() + "'");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> createProduct(WarehouseModel model, HttpServletRequest request) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }


        OboiCode oboiCodeFound = oboiCodeRepository.findByName(model.code).orElse(null);
        if (oboiCodeFound == null) {
            final OboiCode oboiCode = new OboiCode();
            oboiCode.setName(model.code);
            oboiCode.setUser(user);
            oboiCodeFound = oboiCodeRepository.save(oboiCode);
        }

        OboiParty oboiPartyFound = oboiPartyRepository.findByName(model.party).orElse(null);
        if (oboiPartyFound == null) {
            final OboiParty oboiParty = new OboiParty();
            oboiParty.setName(model.party);
            oboiParty.setUser(user);
            oboiPartyFound = oboiPartyRepository.save(oboiParty);
        }

        if (warehouseRepository.existsByOboiCode_IdAndOboiParty_Id(oboiCodeFound.getId(), oboiPartyFound.getId())) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Already exists");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        final Warehouse warehouse = new Warehouse();

        warehouse.setOboiCode(oboiCodeFound);
        warehouse.setOboiParty(oboiPartyFound);
        warehouse.setOutcomeQuantity(0);
        warehouse.setIncomeQuantity(0);
        warehouse.setOnHand(0);
        warehouse.setStatus(true);

        warehouseRepository.save(warehouse);
        final Map<String, Object> map = new HashMap<>();
        map.put("details", "Successfully created");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> getIncomeOutcomeAll(HttpServletRequest request, Object controller) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        if (controller instanceof IncomeController) {
            return new ResponseEntity<>(incomeRepository.getAllIncome(), HttpStatus.OK);
        }
        if (controller instanceof OutcomeController) {
            return new ResponseEntity<>(outcomeRepository.getAllOutcome(), HttpStatus.OK);
        }
        return null;
    }

    public ResponseEntity<Object> incomeOutcome(List<IncomeOutcomeModel> list, HttpServletRequest request, Object controller) {
        if (warehouseRepository.checkDifference() != 0) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Internal server error");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        final List<Income> incomes = new ArrayList<>();
        final List<Outcome> outcomes = new ArrayList<>();
        final List<Warehouse> warehouseList = new ArrayList<>();

        for (IncomeOutcomeModel model : list) {
            final OboiCode oboiCode = oboiCodeRepository.findById(model.code).orElse(null);
            final OboiParty oboiParty = oboiPartyRepository.findById(model.party).orElse(null);

            final Warehouse warehouse = warehouseRepository.findByOboiCode_IdAndOboiParty_Id(model.code, model.party);

            if (controller instanceof IncomeController) {
                warehouse.setIncomeQuantity(warehouse.getIncomeQuantity() + model.quantity);
                warehouse.setOnHand(warehouse.getOnHand() + model.quantity);

                final IncomeType incomeType = incomeTypeRepository.findById(model.type).orElse(null);
                final Income income = new Income();
                income.setOboiCode(oboiCode);
                income.setOboiParty(oboiParty);
                income.setQuantity(model.quantity);
                income.setUser(user);
                income.setIncomeType(incomeType);
                income.setRegNo(regSet());

                incomes.add(income);
            }
            if (controller instanceof OutcomeController) {
                warehouse.setOutcomeQuantity(warehouse.getOutcomeQuantity() + model.quantity);
                warehouse.setOnHand(warehouse.getOnHand() - model.quantity);

                final OutcomeType outcomeType = outcomeTypeRepository.findById(model.type).orElse(null);
                final Outcome outcome = new Outcome();
                outcome.setOboiCode(oboiCode);
                outcome.setOboiParty(oboiParty);
                outcome.setQuantity(model.quantity);
                outcome.setUser(user);
                outcome.setOutcomeType(outcomeType);
                outcome.setRegNo(regSet());

                outcomes.add(outcome);
            }

            warehouseList.add(warehouse);
        }

        warehouseRepository.saveAll(warehouseList);
        if (controller instanceof IncomeController) incomeRepository.saveAll(incomes);
        if (controller instanceof OutcomeController) outcomeRepository.saveAll(outcomes);
        regSetup();

        final Map<String, Object> map = new HashMap<>();
        map.put("details", "Successfully created");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> incomeTypes(HttpServletRequest request) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(incomeTypeRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Object> outcomeTypes(HttpServletRequest request) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(outcomeTypeRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Object> incomeTypesAdd(NameModel model, HttpServletRequest request) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        final IncomeType incomeType = new IncomeType();
        incomeType.setName(model.name);

        incomeTypeRepository.save(incomeType);
        final Map<String, Object> map = new HashMap<>();
        map.put("details", "Successfully created");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> outcomeTypesAdd(NameModel model, HttpServletRequest request) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        final OutcomeType outcomeType = new OutcomeType();
        outcomeType.setName(model.name);

        outcomeTypeRepository.save(outcomeType);
        final Map<String, Object> map = new HashMap<>();
        map.put("details", "Successfully created");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private String regSet() {
        return regRepository.findAll().get(0).getRegNo();
    }

    private void regSetup() {
        final Reg reg = regRepository.findAll().get(0);
        final Integer year = Year.now().getValue();
        final String regNo = year + "-" + (Integer.parseInt(reg.getRegNo().split("-")[1]) + 1);
        reg.setId(1L);
        reg.setRegNo(regNo);
        regRepository.save(reg);
    }
}
