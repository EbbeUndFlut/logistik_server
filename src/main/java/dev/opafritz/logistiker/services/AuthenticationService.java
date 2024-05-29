package dev.opafritz.logistiker.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.opafritz.logistiker.dtos.AuthDto;
import dev.opafritz.logistiker.entities.User;
import dev.opafritz.logistiker.repositories.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User signup(AuthDto authDto) {
        User user = new User();
        user.setEmail(authDto.getEmail());
        user.setPassword(passwordEncoder.encode(authDto.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(AuthDto authDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
        return userRepository.findByEmail(authDto.getEmail()).orElseThrow();
    }
}
