package e_Commerse.Application.model.controller;



import e_Commerse.Application.model.Entities.DTOs.AuthRequest;

import e_Commerse.Application.model.Entities.DTOs.AuthResponse;

import e_Commerse.Application.model.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private  AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        System.out.println("LOGIN REQUEST: " + request.getEmail() + " | " + request.getUsername());
        System.out.println("Login payload => email: " + request.getEmail() + " password: " + request.getPassword());
        return authService.login(request);
    }
}

