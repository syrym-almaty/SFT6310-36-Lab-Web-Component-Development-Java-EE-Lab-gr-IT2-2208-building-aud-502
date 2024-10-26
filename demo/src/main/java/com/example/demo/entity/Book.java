package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class Book {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @Pattern(regexp = "\\d{13}", message = "ISBN must be 13 digits")
    private String isbn;


    private boolean available;

    public Book(){}

    public Book(String title, String author, String isbn, boolean available){
        
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;

        return;
    }

    public String getTitle(){
        return this.title;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getIsbn(){
        return this.isbn;
    }
    public boolean isAvailable(){
        return this.available;
    }
    public UUID getId(){
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
        return;
    }
    public void setAuthor(String author){
        this.author = author;
        return;
    }
    public void setIsbn(String isbn){
        this.isbn = isbn;
        return;
    }
    public void setAvailable(boolean available){
        this.available = available;
        return;
    }
}