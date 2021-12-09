package com.library.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "book")
@Data
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;


    @Column(name = "pagenumber")
    private int pageNumber;

    @Column(name = "publishdate")
    private Date publishDate;

    @Column(name = "image")
    private String image;

    @Column(name = "category")
    private String category;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "bookstatus")
    private String bookStatus;

    @OneToOne(mappedBy = "bookEntity")
    private BookManagementEntity bookManagementEntity;
}
