package com.dataspin.oboiservice.controller;

import com.dataspin.oboiservice.service.OboiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/api/oboi")
public class OboiController {

    private final OboiService oboiService;

    @GetMapping("/code/all")
    public ResponseEntity<Object> getAllOboiCode(HttpServletRequest request) {
        return oboiService.getAllOboiCode(request);
    }

    @GetMapping("/party/all")
    public ResponseEntity<Object> getAllOboiParty(HttpServletRequest request) {
        return oboiService.getAllOboiParty(request);
    }
}
