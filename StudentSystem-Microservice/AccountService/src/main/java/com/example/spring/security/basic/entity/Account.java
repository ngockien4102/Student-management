package com.example.spring.security.basic.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;

    @ManyToMany(fetch = EAGER)
    private Set<Roles> roles = new HashSet<>();

    private String status;
}
