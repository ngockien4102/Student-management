package com.library.dto.Request;


import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BookRequest {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String author;

    @NotEmpty
    private String description;

    @Pattern(regexp = "^[0-9]+$",message = "must be a number")
    private int pageNumber;

    private Date publishDate;

    @NotEmpty
    private String image;

    @NotEmpty
    private String category;

    @Min(value = 0,message = "quantity must be a positive number")
    private int quantity;

    @Pattern(regexp = "^(active|inactive)+$",message = "please input status active or inactive")
    private String bookStatus;

}
