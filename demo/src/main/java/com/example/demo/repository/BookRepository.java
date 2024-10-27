package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
//public interface BookRepository extends JpaRepository<Book, UUID> {
//}

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
    List<Book> findByGenreIgnoreCase(String genre);
    List<Book> findByAvailable(boolean available);
}
