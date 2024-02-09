package com.dataspin.oboiservice.controller;

import com.dataspin.oboiservice.model.CodeIdAndPartyIdModel;
import com.dataspin.oboiservice.model.WarehouseModel;
import com.dataspin.oboiservice.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private WarehouseService warehouseService;

    @PostMapping("/new")
    public ResponseEntity<Object> createProduct(@RequestBody WarehouseModel model, HttpServletRequest request) {
        return warehouseService.createProduct(model, request);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(HttpServletRequest request) {
        return warehouseService.getAllProduct(request);
    }

    @PostMapping("/change-status")
    public ResponseEntity<Object> changeProductStatus(@RequestBody CodeIdAndPartyIdModel model, HttpServletRequest request) {
        return warehouseService.changeProductStatus(model, request);
    }
}
