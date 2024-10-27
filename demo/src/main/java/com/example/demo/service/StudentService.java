package com.example.demo.service;

import com.example.demo.entity.Grade;
import com.example.demo.entity.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.DoubleStream;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(UUID id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(UUID id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    public double calculateGPA(UUID studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));

        List<Grade> grades = student.getGrades();

        double gpa = grades.isEmpty() ? 0.0 :
                grades.stream()
                        .mapToDouble(Grade::getScore)
                        .average()
                        .orElse(0.0);

        student.setGpa(gpa);
        studentRepository.save(student);

        return gpa;
    }
}
