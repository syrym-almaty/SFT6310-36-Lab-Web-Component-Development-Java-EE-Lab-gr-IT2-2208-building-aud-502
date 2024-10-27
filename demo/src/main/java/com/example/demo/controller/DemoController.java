package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Импортируем аннотацию для авторизации
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demo")
@Tag(name = "Demo Controller", description = "This is a demo controller for educational purposes")
public class DemoController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Say Hello", description = "This endpoint returns a simple greeting message.")
    @ApiResponse(responseCode = "200", description = "Greeting message returned successfully")
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, Swagger!";
    }

    @Operation(summary = "Greet User", description = "Returns a greeting message with a note in JSON format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved greeting message",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Greeting.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/greet")
    public Greeting greetUser() {
        return new Greeting("Greetings from the Demo Controller!", "Have a great day!");
    }

    @Operation(summary = "Create User", description = "Creates a new user by accepting a JSON body with name and email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(
            @Parameter(description = "User object containing name, email, and password", required = true)
            @RequestBody User user) {
        User newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @Operation(summary = "Admin Action", description = "This action is restricted to admin users only.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin action performed successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/admin/action")
    public ResponseEntity<String> adminAction() {
        return ResponseEntity.ok("This action can only be performed by an admin.");
    }
}

class Greeting {
    private String message;
    private String note;

    public Greeting(String message, String note) {
        this.message = message;
        this.note = note;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

