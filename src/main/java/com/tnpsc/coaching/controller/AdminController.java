package com.tnpsc.coaching.controller;

import com.tnpsc.coaching.entity.Role;
import com.tnpsc.coaching.entity.User;
import com.tnpsc.coaching.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
// Remove @PreAuthorize
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/students")
    public List<User> getAllStudents() {
        return userService.getAllStudents();
    }

    @GetMapping("/staff")
    public List<User> getAllStaff() {
        return userService.getAllStaff();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}/role")
    public User updateUserRole(@PathVariable Long id, @RequestParam Role role) {
        return userService.updateUserRole(id, role);
    }
}