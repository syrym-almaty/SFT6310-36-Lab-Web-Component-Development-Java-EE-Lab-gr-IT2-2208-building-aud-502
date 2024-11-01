package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.entity.GradeId;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    @EmbeddedId
    private GradeId id = new GradeId();

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    private Course course;

    @NotNull
    private Double score;

    public Double getScore(){
        return score;
    }
}

