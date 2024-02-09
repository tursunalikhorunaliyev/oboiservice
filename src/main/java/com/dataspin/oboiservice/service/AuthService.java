package com.dataspin.oboiservice.service;

import com.dataspin.oboiservice.entity.User;
import com.dataspin.oboiservice.model.UserModel;
import com.dataspin.oboiservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Configuration
public class AuthService {

    private final UserRepository userRepository;

    public ResponseEntity<Map<String, Object>> register(UserModel model) {
        final User user = new User();
        user.setUsername(model.username);
        user.setPassword(model.password);
        userRepository.save(user);
        final Map<String, Object> map = new HashMap<>();
        map.put("details", "Register has been successful");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> login(UserModel model) {
        final String token = generateToken(model.username, model.password);
        final User user = parseToken(token);
        if (user == null) {
            final Map<String, Object> map = new HashMap<>();
            map.put("detail", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        final Map<String, Object> map = new HashMap<>();
        map.put("detail", "Login has been successful");
        map.put("token", token);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public boolean checkUserIsLogged(User user) {
        return user != null;
    }

    private String generateToken(String username, String password) {
        final String source = username + "~" + password;
        final byte[] tokenBytes = Base64.getEncoder().encode(source.getBytes());
        return new String(tokenBytes);
    }

    private User parseToken(String token) {
        if (token == null) return null;
        final byte[] tokenBytesDecoded = Base64.getDecoder().decode(token.getBytes());
        final String userPass = new String(tokenBytesDecoded);
        System.out.println(userPass);
        final String username = userPass.split("~")[0];
        final String password = userPass.split("~")[1];
        final User user = userRepository.getByUsername(username);
        if (!user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    public User parseRequest(HttpServletRequest request) {
        final String token = request.getHeader("Token");
        return parseToken(token);
    }
}
