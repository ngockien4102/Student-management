package com.student.controller;

import com.student.dto.Request.InsertRequest;
import com.student.dto.Request.StudentRequest;
import com.student.dto.Response.ExceptionResponse;
import com.student.dto.Response.StudentResponse;
import com.student.entity.StudentEntity;
import com.student.exception.BadRequestException;
import com.student.exception.ForbiddenException;
import com.student.service.IStudentService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class StudentController {
    @Autowired
    IStudentService iStudentService;

    //API add new student
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = StudentRequest.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @PostMapping(value = "/int/students")
    @CrossOrigin
    public InsertRequest insertStudent(@Valid @RequestBody InsertRequest insertRequest, @RequestHeader("AUTHORIZATION") String token) {
        return iStudentService.insertStudent(insertRequest, token);
    }

    //API get all student
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @GetMapping(value = "ext/students")
    @CrossOrigin
    public List<StudentResponse> getListStudent(@RequestHeader("AUTHORIZATION")String token) {
        return iStudentService.getListStudent(token);
    }


    //API get information of student by ID
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = StudentEntity.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @GetMapping(value = "/ext/students/{id}")
    @CrossOrigin
    public StudentResponse getStudentById(@Valid @PathVariable(name = "id") Long id, @RequestHeader("AUTHORIZATION") String token) {
        return iStudentService.getStudentById(id, token);
    }


    // API update student
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = StudentRequest.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @PutMapping(value = "/int/students/{id}")
    @CrossOrigin
    public StudentRequest updateStudent(@Valid @RequestBody StudentRequest studentDto, @RequestHeader("AUTHORIZATION") String token) {
        return iStudentService.updateStudent(studentDto, token);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @GetMapping(value = "/int/student/checkgraduate")
    @CrossOrigin
    public String checkStudentCondition(@RequestHeader("AUTHORIZATION") String token) {
        return iStudentService.checkgraduateCondition(token);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @GetMapping(value = "/int/student/getBookBorrow/{username}")
    @CrossOrigin
    public List<String> getBookBorrow(@RequestHeader("AUTHORIZATION") String token){
        List<String> bookBorrow = iStudentService.getBookBorrow(token);
        return bookBorrow;
    }
}
