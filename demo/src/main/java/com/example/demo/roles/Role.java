package com.example.demo.roles;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {
    @Id
    private String name;


    @Override
    public String getAuthority() {
        return null;
    }
}
