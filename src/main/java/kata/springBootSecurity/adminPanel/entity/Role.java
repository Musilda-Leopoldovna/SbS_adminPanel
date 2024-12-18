package ru.kata.spring.boot_security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
public class Role implements GrantedAuthority {

    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role() {
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
