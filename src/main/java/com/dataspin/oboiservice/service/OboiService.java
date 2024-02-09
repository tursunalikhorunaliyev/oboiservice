package com.dataspin.oboiservice.service;

import com.dataspin.oboiservice.entity.User;
import com.dataspin.oboiservice.repository.OboiCodeRepository;
import com.dataspin.oboiservice.repository.OboiPartyRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Configuration
public class OboiService {

    private final AuthService authService;
    private final OboiCodeRepository oboiCodeRepository;
    private final OboiPartyRepository oboiPartyRepository;

    public ResponseEntity<Object> getAllOboiCode(HttpServletRequest request) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(oboiCodeRepository.getAllInfo(), HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllOboiParty(HttpServletRequest request) {
        final User user = authService.parseRequest(request);
        if (!authService.checkUserIsLogged(user)) {
            final Map<String, Object> map = new HashMap<>();
            map.put("details", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(oboiPartyRepository.getAllInfo(), HttpStatus.OK);
    }
}
