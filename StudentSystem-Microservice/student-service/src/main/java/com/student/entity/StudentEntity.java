 package com.student.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Component
@Table(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id",unique = true,nullable = false)
    private long id;

    @NotNull
    @Column(name = "roll_number")
    private String rollNumber;

    @Column(name = "name")
    private String name;

    @Column(name="gender")
    private boolean gender;

    @Column(name="date_birth")
    private Date dob;

    @Column(name = "status")
    private String status;

    @Column(name = "user_name")
    private String userName;

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
