package com.example.demo.factory;

import com.example.demo.entity.Grade;
import com.example.demo.entity.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StudentRepository;

import java.util.Set;

public class GPA_Strategy {

    //couldn't autowire
    private StudentRepository studentRepository;

    public Double calculateGPA(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Set<Grade> grades = student.getGrades();
        if (grades.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Grade grade : grades) {
            int credits = grade.getCourse().getCredits();
            totalPoints += grade.getScore() * credits;
            totalCredits += credits;
        }

        Double gpa = totalPoints / totalCredits;
        student.setGpa(gpa); // Set the calculated GPA in the Student entity

        return gpa;
    }
}
