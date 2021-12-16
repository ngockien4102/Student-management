package com.student.service;

import com.student.dto.Request.InsertRequest;
import com.student.dto.Request.StudentRequest;
import com.student.dto.Response.StudentResponse;
import com.student.entity.StudentEntity;

import java.util.List;
import java.util.Optional;


public interface IStudentService {
    StudentResponse getStudentById(long id, String token);

    StudentRequest updateStudent(StudentRequest studentDto,String token);

    InsertRequest insertStudent(InsertRequest insertRequest, String token);

    List<StudentResponse> getListStudent(String token);

    boolean checkgraduateCondition(String token);

    String getBookBorrow( String token);

    List<StudentResponse> getListGraduateStudent(String token);
}
