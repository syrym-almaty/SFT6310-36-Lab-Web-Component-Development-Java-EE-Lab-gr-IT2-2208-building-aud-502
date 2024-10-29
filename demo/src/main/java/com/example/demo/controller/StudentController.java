package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Controller", description = "CRUD operations for Students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Operation(summary = "Get All Students", description = "Retrieve a list of all students")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @Operation(summary = "Create Student", description = "Create a new student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public Student createStudent(
            @Parameter(description = "Student object to be created", required = true)
            @RequestBody Student student) {
//        return studentService.createStudent(student);
        if (studentRepository.existsById(student.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student with this ID already exists.");
        }
        return studentService.createStudent(student);
    }

    @Operation(summary = "Get Student by ID", description = "Retrieve a student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public Student getStudentById(
            @Parameter(description = "UUID of the student to retrieve", required = true)
            @PathVariable UUID id) {
        return studentService.getStudentById(id);
    }

    @Operation(summary = "Update Student", description = "Update an existing student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public Student updateStudent(
            @Parameter(description = "UUID of the student to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Updated student object", required = true)
            @RequestBody Student updatedStudent) {
        return studentService.updateStudent(id, updatedStudent);
    }

    @Operation(summary = "Delete Student", description = "Delete a student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("/{id}")
    public void deleteStudent(
            @Parameter(description = "UUID of the student to delete", required = true)
            @PathVariable UUID id) {
        studentService.deleteStudent(id);
    }
    @Operation(summary = "Calculate GPA for a student")
    @ApiResponse(responseCode = "200", description = "GPA calculated successfully")
    @GetMapping("/{id}/calculateGPA")
    public ResponseEntity<Double> calculateGPA(@PathVariable UUID id) {
        double gpa = studentService.calculateGPA(id);
        return ResponseEntity.ok(gpa);
    }
}