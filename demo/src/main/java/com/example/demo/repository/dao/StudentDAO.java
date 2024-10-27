package com.example.demo.repository.dao;

import com.example.demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Student findById(Long id){
        return entityManager.find(Student.class, id);
    }

    public Student findByName(String name){
        return entityManager.find(Student.class, name);
    }

    public Student findByEmail(String email){
        return entityManager.find(Student.class, email);
    }

    public void save(Student student){
        entityManager.persist(student);
    }
}
