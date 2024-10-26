package com.example.demo.service;

import com.example.demo.dao.CourseDAO;
import com.example.demo.dao.StudentDAO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;

    @Autowired
    public EnrollmentService(StudentDAO studentDAO, CourseDAO courseDAO) {
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
    }

    public void enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentDAO.findById(studentId);
        if (student == null) {
            throw new ResourceNotFoundException("Student not found with id " + studentId);
        }

        Course course = courseDAO.findById(courseId);
        if (course == null) {
            throw new ResourceNotFoundException("Course not found with id " + courseId);
        }

        // Проверка, не превышает ли количество курсов у студента лимит (5 курсов)
        if (student.getCourses().size() >= 5) {
            throw new BusinessException("Cannot enroll in more than 5 courses.");
        }

        // Проверка, не превышает ли количество студентов на курсе лимит (30 студентов)
        if (course.getStudents().size() >= 30) {
            throw new BusinessException("Course capacity reached.");
        }

        // Добавление студента на курс
        student.getCourses().add(course);
        course.getStudents().add(student);

        studentDAO.update(student); // Обновляем данные студента
        courseDAO.update(course);   // Обновляем данные курса
    }
}
