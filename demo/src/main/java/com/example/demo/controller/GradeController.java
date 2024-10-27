package com.example.demo.controller;

import com.example.demo.entity.Grade;
import com.example.demo.service.GradeService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@Tag(name = "Grade Controller", description = "CRUD operations for Grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // Create a new grade
    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade) {
        Grade createdGrade = gradeService.createGrade(grade);
        return ResponseEntity.ok(createdGrade);
    }

    // Get a grade by ID
    @GetMapping("/{studentId}/{courseId}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Long studentId, @PathVariable Long courseId) {
        Grade grade = gradeService.getGradeById(studentId, courseId);
        return ResponseEntity.ok(grade);
    }

    // Get all grades
    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return ResponseEntity.ok(grades);
    }

    // Update an existing grade
    @PutMapping("/{studentId}/{courseId}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long studentId, @PathVariable Long courseId, @RequestBody Grade gradeDetails) {
        Grade updatedGrade = gradeService.updateGrade(studentId, courseId, gradeDetails);
        return ResponseEntity.ok(updatedGrade);
    }

    // Delete a grade
    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long studentId, @PathVariable Long courseId) {
        gradeService.deleteGrade(studentId, courseId);
        return ResponseEntity.noContent().build();
    }
}