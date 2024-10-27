package com.example.demo.service;

import com.example.demo.entity.Grade;
import com.example.demo.entity.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }









    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    // Add other fields as necessary
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }











    public Double calculateGPA(Long studentId) {
        // Fetch all grades for the student
        List<Grade> grades = gradeRepository.findByStudentId(studentId);

        // Calculate the average score (GPA)
        OptionalDouble average = grades.stream()
                .mapToDouble(Grade::getScore)
                .average();

        // Return the calculated GPA or 0.0 if no grades are present
        return average.isPresent() ? average.getAsDouble() : 0.0;
    }


    public Double getStudentGPA(Long studentId) {
        // Ensure the student exists before calculating GPA
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));

        // Calculate GPA using the calculateGPA method
        return calculateGPA(studentId);
    }
}