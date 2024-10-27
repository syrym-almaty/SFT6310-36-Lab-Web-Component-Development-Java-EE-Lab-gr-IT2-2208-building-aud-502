package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateBook() throws Exception {
        String bookJson = "{\"title\": \"Effective Java\", \"author\": \"Joshua Bloch\", \"isbn\": \"9780134685991\", \"available\": true}";

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }

//    @Test
//    public void testCreateBookValidationError() throws Exception {
//        String invalidBookJson = "{\"title\": \"\", \"author\": \"Joshua Bloch\", \"isbn\": \"123\", \"available\": true}";
//
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidBookJson))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.title").value("Title is mandatory"));
//    }

    @Test
    public void testCreateBookValidationError() throws Exception {
        String invalidBookJson = "{\"title\": \"\", \"author\": \"Joshua Bloch\", \"isbn\": \"123\", \"available\": true}";

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBookJson))
                .andExpect(status().isBadRequest())  // Проверяем статус ошибки
                .andExpect(jsonPath("$[0].defaultMessage").value("Title is mandatory"));  // Проверяем сообщение об ошибке
    }

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSearchBooks() throws Exception {
        mockMvc.perform(get("/api/books/search?keyword=Effective"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSearchBooksEmptyKeyword() throws Exception {
        mockMvc.perform(get("/api/books/search?keyword="))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Keyword cannot be empty"));
    }

    @Test
    public void testUpdateBookNotFound() throws Exception {
        String bookJson = "{\"title\": \"Effective Java\", \"author\": \"Joshua Bloch\", \"isbn\": \"9780134685991\", \"available\": true}";

        mockMvc.perform(put("/api/books/{id}", "invalid-uuid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found with ID: invalid-uuid"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        UUID existingBookId = UUID.fromString("valid-uuid-for-existing-book"); // Используйте действительный UUID
        mockMvc.perform(delete("/api/books/{id}", existingBookId))
                .andExpect(status().isNoContent()); // Ожидается 204 No Content
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", "invalid-uuid"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found with ID: invalid-uuid"));
    }

    @Test
    public void testSearchBooksNoResults() throws Exception {
        mockMvc.perform(get("/api/books/search?keyword=NonExistentTitle"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty()); // Проверяем, что массив пустой
    }

    @Test
    public void testGetBooksByGenre() throws Exception {
        mockMvc.perform(get("/api/books/genre?genre=Fiction"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testBorrowBook() throws Exception {
        mockMvc.perform(post("/api/books/borrow/{id}", "valid-uuid")
                        .param("userId", "user-uuid"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book borrowed successfully"));
    }

    @Test
    public void testReturnBook() throws Exception {
        mockMvc.perform(post("/api/books/return/{id}", "valid-uuid"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book returned successfully"));
    }
}
