package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        logger.info("Username: {}", username);
        logger.info("Password: {}", password);
        return authService.register(username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        logger.info("Username: {}", username);
        logger.info("Password: {}", password);

        String token = authService.login(username, password);
        return ResponseEntity.ok(token);  // ✅ This ensures a proper 200 OK response
    }


}
