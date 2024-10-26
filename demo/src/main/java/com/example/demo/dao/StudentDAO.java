package com.example.demo.dao;

import com.example.demo.entity.Student;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class StudentDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Student findById(UUID id) {
        return entityManager.find(Student.class, id);
    }

    public void save(Student student) {
        entityManager.persist(student);
    }

    public void delete(Student student) {
        entityManager.remove(student);
    }

    public List<Student> findAll() {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s", Student.class);
        return query.getResultList();
    }

    public void update(Student student) {
        entityManager.merge(student);
    }

    public List<Student> findByName(String name) {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s WHERE s.name = :name", Student.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    public List<Student> findByEmail(String email) {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
}
