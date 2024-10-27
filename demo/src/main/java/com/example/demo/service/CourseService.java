package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.entity.Course;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Create a new course
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // Get a course by ID
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    // Update an existing course
    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setName(updatedCourse.getName());
                    course.setName(updatedCourse.getName());
                    course.setCode(updatedCourse.getCode());
                    course.setStudents(updatedCourse.getStudents());
                    return courseRepository.save(course);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    // Delete a course
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
