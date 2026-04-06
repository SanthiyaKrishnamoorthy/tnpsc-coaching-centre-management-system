package com.tnpsc.coaching.controller;

import com.tnpsc.coaching.entity.Course;
import com.tnpsc.coaching.entity.User;
import com.tnpsc.coaching.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
// Remove @PreAuthorize
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/students")
    public List<User> getAllStudents() {
        return staffService.getAllStudents();
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return staffService.getAllCourses();
    }

    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return staffService.getCourseById(id);
    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return staffService.updateCourse(id, course);
    }
}