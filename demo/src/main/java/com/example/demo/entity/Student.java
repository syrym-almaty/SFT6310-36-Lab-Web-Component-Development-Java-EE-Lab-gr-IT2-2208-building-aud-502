package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    // GPA field
    private Double gpa;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
    private Set<Grade> grade = new HashSet<>(); ; // Set to hold the grades

    // Constructors
    public Student() {}

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Double getGpa() {
        return gpa;
    }

    public Set<Grade> getGrade() {
        return grade;
    }

    public Set<Grade> getGrades(){return grade;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public void setGrade(Set<Grade> grade) {
        this.grade = grade;
    }
}