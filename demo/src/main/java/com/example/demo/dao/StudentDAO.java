package com.example.demo.dao;

import com.example.demo.entity.Student;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class StudentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    public void save(Student student) {
        entityManager.persist(student);
    }

    public void update(Student student) {
        entityManager.merge(student);
    }

    public void delete(Long id) {
        Student student = findById(id);
        if (student != null) {
            entityManager.remove(student);
        }
    }

}
