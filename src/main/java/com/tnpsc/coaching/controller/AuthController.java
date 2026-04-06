package com.tnpsc.coaching.controller;

import com.tnpsc.coaching.dto.AuthResponse;
import com.tnpsc.coaching.dto.LoginRequest;
import com.tnpsc.coaching.dto.RegisterRequest;
import com.tnpsc.coaching.entity.User;
import com.tnpsc.coaching.security.JwtUtil;
import com.tnpsc.coaching.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);

        // Auto login after registration
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse(token, user.getRole().name(), user.getEmail(), user.getName());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Fix: Get user from database using email from UserDetails
            User user = userService.getUserByEmail(userDetails.getUsername());

            String token = jwtUtil.generateToken(userDetails);
            return new AuthResponse(token, user.getRole().name(), user.getEmail(), user.getName());

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}