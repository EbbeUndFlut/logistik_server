package dev.opafritz.logistiker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.opafritz.logistiker.dtos.AuthDto;
import dev.opafritz.logistiker.dtos.LoginResponseDto;
import dev.opafritz.logistiker.entities.User;
import dev.opafritz.logistiker.services.AuthenticationService;
import dev.opafritz.logistiker.services.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody AuthDto authDto) {
        User registeredUser = authenticationService.signup(authDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDto authDto) {
        User authenticatedUser = authenticationService.authenticate(authDto);
        String jwt = jwtService.buildToken(authenticatedUser);

        return ResponseEntity.ok(jwt);

    }
}
