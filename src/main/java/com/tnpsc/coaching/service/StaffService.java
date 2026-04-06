package com.tnpsc.coaching.service;

import com.tnpsc.coaching.entity.Course;
import com.tnpsc.coaching.entity.User;
import com.tnpsc.coaching.repository.CourseRepository;
import com.tnpsc.coaching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    // Staff can view all students
    public List<User> getAllStudents() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().name().equals("STUDENT"))
                .toList();
    }

    // Staff can update course details (but not create/delete)
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setCourseName(courseDetails.getCourseName());
        course.setDescription(courseDetails.getDescription());
        course.setPrice(courseDetails.getPrice());

        return courseRepository.save(course);
    }

    // Staff can view all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Staff can view course by ID
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
}