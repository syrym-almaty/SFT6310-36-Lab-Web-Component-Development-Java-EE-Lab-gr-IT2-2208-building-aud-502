package com.example.demo.service;

import com.example.demo.dao.StudentDAO;
import com.example.demo.entity.Grade;
import com.example.demo.entity.Student;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    private final StudentDAO studentDAO;

    @Autowired
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll(); // Реализовать метод findAll в StudentDAO
    }

    public Student createStudent(Student student) {
        studentDAO.save(student);
        return student;
    }

    public Student getStudentById(Long id) {
        return Optional.ofNullable(studentDAO.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Студент не найден с id " + id));
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student student = getStudentById(id);
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        studentDAO.update(student); // Метод update в StudentDAO
        return student;
    }

    public void deleteStudent(Long id) {
        studentDAO.delete(id); // Метод delete в StudentDAO
    }

    public Double calculateGPA(Long studentId) {
        Student student = studentDAO.findById(studentId);
        if (student == null) {
            throw new ResourceNotFoundException("Студент не найден с id " + studentId);
        }

        Set<Grade> grades = student.getGrades();
        if (grades.isEmpty()) {
            return 0.0; // Возвращает 0.0, если у студента нет оценок
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        // Подсчитываем общее количество баллов и кредитов
        for (Grade grade : grades) {
            int credits = grade.getCourse().getCredits();
            totalPoints += grade.getScore() * credits;
            totalCredits += credits;
        }

        return totalPoints / totalCredits; // Рассчитываем средний балл
    }
}
