package com.example.restapinotes.auth;

import com.example.restapinotes.auth.dto.LoginRequest;
import com.example.restapinotes.auth.dto.LoginResponse;
import com.example.restapinotes.auth.dto.RegistrationRequest;
import com.example.restapinotes.auth.dto.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public RegistrationResponse register(@RequestBody RegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse register(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}