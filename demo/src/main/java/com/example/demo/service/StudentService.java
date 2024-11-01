package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.entity.Grade;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

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
                    // Add other fields as necessary
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    public Double getGPAById(UUID id){

        Student student = studentRepository.findById(id).orElse(null);

        if(student == null){
            throw new ResourceNotFoundException("Student not found with id " + id);
        }

        Set<Grade> grades = student.getGrades();
        if (grades.isEmpty()) {
            return 0.0;
        }

        Double grades_sum = new Double(0);
        for (Grade grade : grades){
            grades_sum += grade.getScore();
        }

        return grades_sum / grades.size();
    }
}
