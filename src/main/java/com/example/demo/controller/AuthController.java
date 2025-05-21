package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;


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
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password) {
//        logger.info("Username: {}", username);
//        logger.info("Password: {}", password);
        String token = authService.login(username, password);
        boolean isOnboarded = authService.is_onboarded();
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("onboarded", isOnboarded);

        return ResponseEntity.ok(response);
    }
}
@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Slf4j
class Operations{
    private final UserRepository userRepository;
    private final OperationService OpService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String leetcode,
            @RequestParam String codechef,
            @RequestParam String codeforces,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        OpService.saveOrUpdateProfile(user, leetcode, codechef, codeforces);


        return ResponseEntity.ok("Profile saved successfully.");
    }


}

