package com.library.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;

    private String name;

    private String author;

    private String description;

    private int pageNumber;

    private Date publishDate;

    private String image;

    private String category;

    private int quantity;

    private String bookStatus;
}
