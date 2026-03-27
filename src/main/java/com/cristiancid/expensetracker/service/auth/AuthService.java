package com.cristiancid.expensetracker.service.auth;

import com.cristiancid.expensetracker.dto.auth.AuthResponse;
import com.cristiancid.expensetracker.dto.auth.LoginRequest;
import com.cristiancid.expensetracker.dto.auth.RegisterRequest;
import com.cristiancid.expensetracker.exception.EmailAlreadyExistsException;
import com.cristiancid.expensetracker.exception.InvalidCredentialsException;
import com.cristiancid.expensetracker.model.User;
import com.cristiancid.expensetracker.repository.UserRepository;
import com.cristiancid.expensetracker.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("user with this email already exists");
            }

            String encodedPassword = passwordEncoder.encode(request.getPassword());
            User newUser = new User(request.getName(), request.getEmail(), encodedPassword);
            User savedUser = userRepository.save(newUser);
            String token = jwtService.generateToken(savedUser.getEmail());
            return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
