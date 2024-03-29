package com.dataspin.oboiservice.controller;

import com.dataspin.oboiservice.entity.SortDateModel;
import com.dataspin.oboiservice.model.IncomeOutcomeModel;
import com.dataspin.oboiservice.model.NameModel;
import com.dataspin.oboiservice.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/income")
public class IncomeController {

    private final WarehouseService warehouseService;

    @PostMapping("/new")
    public ResponseEntity<Object> createIncome(@RequestBody List<IncomeOutcomeModel> list, HttpServletRequest request) {
        return warehouseService.incomeOutcome(list, request, this);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(HttpServletRequest request) {
        return warehouseService.getIncomeOutcomeAll(request, this);
    }

    @PostMapping("/types/add")
    public ResponseEntity<Object> typeAdd(@RequestBody NameModel model, HttpServletRequest request) {
        return warehouseService.incomeTypesAdd(model, request);
    }

    @GetMapping("/types")
    public ResponseEntity<Object> types(HttpServletRequest request) {
        return warehouseService.incomeTypes(request);
    }

    @GetMapping("/sort-by-date")
    public ResponseEntity<Object> sortByDate(@RequestBody SortDateModel model, HttpServletRequest request) {
        return warehouseService.sortByDateIncomeOutcome(model, request, this);
    }
}
