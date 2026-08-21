package com.smart_park.controller;

import com.smart_park.auth.JwtUtil;
import com.smart_park.dto.auth.LoginRequest;
import com.smart_park.dto.auth.LoginResponse;
import com.smart_park.exceptions.auth.InvalidCredentialsProvidedException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${app.security.username}")
    private String validUsername;

    @Value("${app.security.password}")
    private String validPassword;

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        if (!validUsername.equals(request.getUsername())
                || !validPassword.equals(request.getPassword())) {

            throw new InvalidCredentialsProvidedException(
                    "Invalid username or password."
            );
        }

        String token = jwtUtil.generateToken(request.getUsername());

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
