package com.tnpsc.coaching.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class DebugController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/auth-check")
    public Map<String, Object> authCheck() {
        Map<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        response.put("isAuthenticated", auth != null && !"anonymousUser".equals(auth.getPrincipal()));

        if (auth != null) {
            response.put("username", auth.getName());
            response.put("authorities", auth.getAuthorities().toString());
            response.put("principalType", auth.getPrincipal() != null ?
                    auth.getPrincipal().getClass().getSimpleName() : "null");
        }

        return response;
    }

    @PostMapping("/echo")
    public Map<String, Object> echo(@RequestBody(required = false) Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        response.put("method", "POST");
        response.put("body", body);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}