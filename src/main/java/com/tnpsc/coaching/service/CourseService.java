package com.tnpsc.coaching.service;

import com.tnpsc.coaching.entity.Course;
import com.tnpsc.coaching.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Admin creates course
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // Student views all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get course by id
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
}