package com.library.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "bookmanagement")
public class BookManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "book_id",referencedColumnName = "id")
    private BookEntity bookEntity;
}
