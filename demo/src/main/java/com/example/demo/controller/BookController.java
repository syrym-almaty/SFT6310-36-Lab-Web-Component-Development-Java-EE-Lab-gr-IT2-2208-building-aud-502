package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("keyword") String keyword) {
        List<Book> books = bookService.searchBooks(keyword);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam String genre) {
        List<Book> books = bookService.findByGenre(genre);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Метод для поиска книг по заголовку или автору
//    @GetMapping("/search")
//    public List<Book> searchBooks(@RequestParam String keyword) {
//        return bookService.searchBooks(keyword);
//    }

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PostMapping("/borrow/{id}")
    public ResponseEntity<?> borrowBook(@PathVariable UUID id, @RequestParam UUID userId) {
        bookService.borrowBook(id, userId);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<?> returnBook(@PathVariable UUID id) {
        bookService.returnBook(id);
        return ResponseEntity.ok("Book returned successfully");
    }
}

