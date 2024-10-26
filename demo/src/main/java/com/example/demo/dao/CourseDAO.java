package com.example.demo.dao;

import com.example.demo.entity.Course;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CourseDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    public void save(Course course) {
        entityManager.persist(course);
    }

    public void delete(Course course) {
        entityManager.remove(entityManager.contains(course) ? course : entityManager.merge(course));
    }

    public List<Course> findAll() {
        TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c", Course.class);
        return query.getResultList();
    }

    public void update(Course course) {
        entityManager.merge(course);
    }

    public List<Course> findByName(String name) {
        TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c WHERE c.name = :name", Course.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
