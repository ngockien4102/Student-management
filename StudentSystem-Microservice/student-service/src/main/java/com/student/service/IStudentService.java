package com.student.service;

import com.student.dto.Request.StudentRequest;
import com.student.entity.StudentEntity;

import java.util.List;
import java.util.Optional;


public interface IStudentService {
    StudentEntity getStudentById(long id,String token);

    StudentRequest updateStudent(StudentRequest studentDto,String token);

    StudentRequest insertStudent(StudentRequest studentDto,String token);

    List<StudentRequest> getListStudent();

    String checkgraduateCondition(String rollnumber,String token);

    List<String> getBookBorrow(String username, String token);
}
