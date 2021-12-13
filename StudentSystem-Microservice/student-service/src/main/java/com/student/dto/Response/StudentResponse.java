package com.student.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private long id;

    private String rollNumber;

    private String name;

    private String gender;

    private Date dob;

    private String status;

    private String userName;
}
