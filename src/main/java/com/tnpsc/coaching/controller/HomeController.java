package com.tnpsc.coaching.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "TNPSC Coaching Backend Running Successfully!";
    }

    @GetMapping("/test-auth")
    public Map<String, Object> testAuth(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if (authentication != null) {
            response.put("authenticated", true);
            response.put("name", authentication.getName());
            response.put("authorities", authentication.getAuthorities().toString());
            response.put("details", authentication.getDetails() != null ?
                    authentication.getDetails().toString() : null);
            response.put("principal", authentication.getPrincipal() != null ?
                    authentication.getPrincipal().getClass().getSimpleName() : null);
        } else {
            response.put("authenticated", false);
            response.put("message", "No authentication found");
        }

        return response;
    }

    @GetMapping("/test-admin")
    public String testAdmin() {
        return "If you see this, you have ADMIN role!";
    }

    @GetMapping("/test-roles")
    public Map<String, Object> testRoles(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if (authentication != null) {
            response.put("username", authentication.getName());
            response.put("isAuthenticated", authentication.isAuthenticated());

            // Check specific roles
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isStaff = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"));
            boolean isStudent = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));

            response.put("hasRole_ADMIN", isAdmin);
            response.put("hasRole_STAFF", isStaff);
            response.put("hasRole_STUDENT", isStudent);
            response.put("allAuthorities", authentication.getAuthorities());
        } else {
            response.put("error", "No authentication found");
        }

        return response;
    }
}