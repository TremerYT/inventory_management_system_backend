package com.ims.inventory_management_system_backend.controller.auth;

import com.ims.inventory_management_system_backend.dto.login.LoginRequestDTO;
import com.ims.inventory_management_system_backend.dto.register.RegisterRequestDTO;
import com.ims.inventory_management_system_backend.dto.token.RefreshTokenRequest;
import com.ims.inventory_management_system_backend.dto.token.TokenPair;
import com.ims.inventory_management_system_backend.service.auth.AuthService;
import com.ims.inventory_management_system_backend.service.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        authService.registerUser(registerRequest);
        return new ResponseEntity<>("Customer created Successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest){
        TokenPair tokenPair = authService.loginUser(loginRequest);
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        TokenPair tokenPair = authService.refreshToken(request);
        return ResponseEntity.ok(tokenPair);
    }
}
