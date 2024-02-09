package com.dataspin.oboiservice.controller;

import com.dataspin.oboiservice.model.IncomeOutcomeModel;
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

    @GetMapping("/types")
    public ResponseEntity<Object> types(HttpServletRequest request) {
        return warehouseService.incomeTypes(request);
    }
}
