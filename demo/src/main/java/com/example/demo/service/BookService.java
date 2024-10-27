package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book updateBook(UUID id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setAvailable(bookDetails.isAvailable());
            return bookRepository.save(book);
        }
        return null;
    }

//    public void deleteBook(UUID id) {
//        bookRepository.deleteById(id);
//    }
    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

//    public List<Book> searchBooks(String keyword) {
//        return bookRepository.findAll().stream()
//                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
//                        book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
//                .toList();
//    }
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
    }

    public Book findBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }

    public void borrowBook(UUID id, UUID userId) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for borrowing");
        }

        book.setAvailable(false);
        book.setBorrowedBy(userId);
        book.setBorrowedDate(LocalDate.now());
        bookRepository.save(book);
    }

    public void returnBook(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setBorrowedDate(null);
        bookRepository.save(book);
    }
}
