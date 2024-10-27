package com.example.demo.service;

import com.example.demo.entity.Grade;
import com.example.demo.entity.GradeId;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.GradingStrategy;
import com.example.demo.factory.GradingStrategyFactory;
import com.example.demo.manager.GradingManager;
import com.example.demo.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;


    // Create a new grade
    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    // Get a grade by studentId and courseId
    public Grade getGradeById(Long studentId, Long courseId) {
        return gradeRepository.findById(new GradeId(studentId, courseId))
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with studentId " + studentId + " and courseId " + courseId));
    }

    // Get all grades
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    // Update an existing grade
    public Grade updateGrade(Long studentId, Long courseId, Grade gradeDetails) {
        return gradeRepository.findById(new GradeId(studentId, courseId))
                .map(grade -> {
                    grade.setScore(gradeDetails.getScore());
                    // Update other fields if necessary
                    return gradeRepository.save(grade);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with studentId " + studentId + " and courseId " + courseId));
    }

    // Delete a grade
    public void deleteGrade(Long studentId, Long courseId) {
        GradeId gradeId = new GradeId(studentId, courseId);
        if (!gradeRepository.existsById(gradeId)) {
            throw new ResourceNotFoundException("Grade not found with studentId " + studentId + " and courseId " + courseId);
        }
        gradeRepository.deleteById(gradeId);
    }



    public Double applyGradingStrategy(Long studentId, Long courseId, String strategyType) {
        Grade grade = getGradeById(studentId, courseId);
        GradingManager gradingManager = GradingManager.getInstance(); // Ensure one instance
        return gradingManager.applyStrategy(strategyType, grade.getScore());
    }
}
