package com.tnpsc.coaching.config;

import com.tnpsc.coaching.entity.Course;
import com.tnpsc.coaching.entity.Role;
import com.tnpsc.coaching.entity.User;
import com.tnpsc.coaching.repository.CourseRepository;
import com.tnpsc.coaching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin if not exists
        if (userRepository.findByEmail("admin@tnpsc.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@tnpsc.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        // Create sample courses
        if (courseRepository.count() == 0) {
            Course course1 = new Course();
            course1.setCourseName("General Studies");
            course1.setDescription("Complete general studies for TNPSC Group 1,2,4");
            course1.setPrice(5000.0);
            courseRepository.save(course1);

            Course course2 = new Course();
            course2.setCourseName("Tamil Eligibility Test");
            course2.setDescription("Tamil language and literature for TNPSC exams");
            course2.setPrice(3000.0);
            courseRepository.save(course2);

            Course course3 = new Course();
            course3.setCourseName("Aptitude & Mental Ability");
            course3.setDescription("Quantitative aptitude and reasoning");
            course3.setPrice(4000.0);
            courseRepository.save(course3);
        }
    }
}