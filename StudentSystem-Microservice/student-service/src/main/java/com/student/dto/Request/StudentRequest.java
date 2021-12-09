package com.student.dto.Request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StudentRequest {

    @NotNull
    private long id;

    @NotNull
    private String rollNumber;

    @NotBlank
    // @Pattern(regexp = "^[a-zA-Z]*$",message = "name must be letter")
    private String name;

    private boolean gender;

    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date dob;

    @Pattern(regexp = "^(pass)|(not pass)$", message = "please input status pass or not pass")
    private String status;
}
