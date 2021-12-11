package com.library.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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


    @OneToMany(mappedBy = "bookEntity",fetch = FetchType.LAZY)
    private List<BookManagementEntity> bookManagementEntities;
}
