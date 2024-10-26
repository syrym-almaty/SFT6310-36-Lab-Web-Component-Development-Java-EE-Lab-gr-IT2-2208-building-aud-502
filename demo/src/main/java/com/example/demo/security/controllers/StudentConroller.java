package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    @GetMapping("/dashboard")
    public String getStudentDashboard() {
        return "Welcome to the Student Dashboard!";
    }

    @GetMapping("/courses")
    public String getStudentCourses() {
        return "Here are your enrolled courses.";
    }

    @GetMapping("/profile")
    public String getStudentProfile() {
        return "Here is your profile information.";
    }
    
    // Add more student-only endpoints as needed
}
