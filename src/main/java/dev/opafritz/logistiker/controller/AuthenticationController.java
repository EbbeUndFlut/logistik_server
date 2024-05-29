package dev.opafritz.logistiker.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.opafritz.logistiker.dtos.AuthDto;
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
    public ResponseEntity<String> login(@RequestBody AuthDto authDto, HttpServletResponse response) {
        User authenticatedUser = authenticationService.authenticate(authDto);
        String jwt = jwtService.buildToken(authenticatedUser);
        response.addCookie(authenticationService.setAuthCookie(jwt));

        return ResponseEntity.ok(jwt);

    }
}
