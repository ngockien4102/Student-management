package com.student.controller;

import com.student.dto.Request.StudentRequest;
import com.student.dto.Response.ExceptionResponse;
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
    public StudentRequest insertStudent(@Valid @RequestBody StudentRequest studentDto, @RequestHeader("AUTHORIZATION") String token) {
        return iStudentService.insertStudent(studentDto, token);
    }

    //API get all student
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @GetMapping(value = "ext/students")
    public List<StudentRequest> getListStudent() {
        return iStudentService.getListStudent();
    }


    //API get information of student by ID
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = StudentEntity.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @GetMapping(value = "/ext/students/{id}")
    public StudentEntity getStudentById(@Valid @PathVariable(name = "id") Long id, @RequestHeader("AUTHORIZATION") String token) {
        return iStudentService.getStudentById(id, token);
    }


    // API update student
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = StudentRequest.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @PutMapping(value = "/int/students/{id}")
    public StudentRequest updateStudent(@Valid @RequestBody StudentRequest studentDto, @RequestHeader("AUTHORIZATION") String token) {
        return iStudentService.updateStudent(studentDto, token);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @GetMapping(value = "/int/student/checkgraduate/{username}")
    public String checkStudentCondition(@PathVariable("username") String username,@RequestHeader("AUTHORIZATION") String token) {
        return iStudentService.checkgraduateCondition(username,token);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)})
    @GetMapping(value = "/int/student/getBookBorrow/{username}")
    public List<String> getBookBorrow(@PathVariable("username") String username,@RequestHeader("AUTHORIZATION") String token){
        List<String> bookBorrow = iStudentService.getBookBorrow(username,token);
        return bookBorrow;
    }
}
