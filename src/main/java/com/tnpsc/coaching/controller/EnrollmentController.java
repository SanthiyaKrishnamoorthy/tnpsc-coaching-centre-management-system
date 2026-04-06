package com.tnpsc.coaching.controller;

import com.tnpsc.coaching.dto.EnrollmentRequest;
import com.tnpsc.coaching.entity.Enrollment;
import com.tnpsc.coaching.entity.EnrollmentStatus;
import com.tnpsc.coaching.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // Student endpoints
    @PostMapping("/student/enroll")
    public Enrollment enrollInCourse(@RequestBody EnrollmentRequest request) {
        return enrollmentService.enrollInCourse(request);
    }

    @GetMapping("/student/my-enrollments")
    public List<Enrollment> getMyEnrollments() {
        return enrollmentService.getMyEnrollments();
    }

    // Staff endpoints
    @GetMapping("/staff/enrollments/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentsByCourse(courseId);
    }

    @PutMapping("/staff/enrollments/{enrollmentId}/status")
    public Enrollment updateEnrollmentStatus(
            @PathVariable Long enrollmentId,
            @RequestParam EnrollmentStatus status) {
        return enrollmentService.updateEnrollmentStatus(enrollmentId, status);
    }

    // Admin endpoints
    @GetMapping("/admin/enrollments")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }
}