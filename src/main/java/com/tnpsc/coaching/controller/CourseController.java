package com.tnpsc.coaching.controller;

import com.tnpsc.coaching.entity.Course;
import com.tnpsc.coaching.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ================= ADMIN =================

    @PostMapping("/admin/courses")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    // ================= STUDENT =================

    @GetMapping("/student/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/student/courses/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }
}