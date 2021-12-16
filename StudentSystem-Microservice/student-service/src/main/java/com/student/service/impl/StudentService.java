package com.student.service.impl;

import com.student.Token.CheckRoleService;
import com.student.Token.GetUserName;
import com.student.dto.Request.InsertRequest;
import com.student.dto.Request.StudentRequest;
import com.student.dto.Response.ExceptionResponse;
import com.student.dto.Response.StudentResponse;
import com.student.entity.StudentEntity;
import com.student.exception.BadRequestException;
import com.student.exception.DuplicateRollnumberException;
import com.student.exception.ErrorCode;
import com.student.exception.ForbiddenException;
import com.student.repository.StudentRepository;
import com.student.restTemplate.RestTemplateService;
import com.student.service.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

//    @Autowired
//    private StudentMapper studentMapper;

    @Autowired
    CheckRoleService checkRoleService;

    @Autowired
    RestTemplateService restTemplate;

    @Autowired
    GetUserName getUserName;

    final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Override
    public StudentResponse getStudentById(long id, String token) throws BadRequestException, ForbiddenException {
        logger.info("===== receive student id {}, token to get student by id =====", id);
        if (checkRoleService.checkRole(token)) {
            StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new BadRequestException(new ExceptionResponse(ErrorCode.notFound)));
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setId(id);
            studentResponse.setRollNumber(studentEntity.getRollNumber());
            studentResponse.setName(studentEntity.getName());
            studentResponse.setGender(studentEntity.getGender());
            studentResponse.setDob(studentEntity.getDob());
            studentResponse.setStatus(studentEntity.getStatus());
            studentResponse.setUserName(studentEntity.getUserName());
            return studentResponse;
        } else {
            logger.error("this user don't have Permision to get");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public StudentRequest updateStudent(StudentRequest studentRequest, String token) throws ForbiddenException, BadRequestException {
        logger.info("===== receive student info {}, token to update =====", studentRequest.toString());
        if (checkRoleService.checkRole(token)) {
            StudentEntity student = student = studentRepository.findById(studentRequest.getId()).orElseThrow(() -> new BadRequestException(new ExceptionResponse(ErrorCode.notFound)));
            student.setRollNumber(studentRequest.getRollNumber());
            student.setName(student.getName());
            student.setGender(student.getGender());
            student.setDob(student.getDob());
            student.setStatus(student.getStatus());
            student.setUserName(student.getUserName());
            studentRepository.save(student);
            return studentRequest;
        } else {
            logger.error("this user don't have Permision to update");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public InsertRequest insertStudent(InsertRequest insertRequest, String token) throws ForbiddenException, DuplicateRollnumberException {
        logger.info("===== receive student info {}, token to insert =====", insertRequest.toString());
        if (checkRoleService.checkRole(token)) {
            if (checkRollNumberDupliacte(insertRequest.getRollNumber())) {
                StudentEntity student = new StudentEntity();
                student.setRollNumber(insertRequest.getRollNumber());
                student.setName(insertRequest.getName());
                student.setGender(insertRequest.getGender());
                student.setDob(insertRequest.getDob());
                student.setStatus(insertRequest.getStatus());
                studentRepository.save(student);
                return insertRequest;
            } else {
                logger.error("this roll number Dupliacte");
                throw new DuplicateRollnumberException(new ExceptionResponse(ErrorCode.duplicateRollnumber));
            }

        } else {
            logger.error("this user don't have Permision to check");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }

    }

    @Override
    public List<StudentResponse> getListStudent(String token) {
        if (checkRoleService.checkRole(token)) {
            List<StudentEntity> listStudent = studentRepository.findAll();
            List<StudentResponse> studentRequestList = new ArrayList<>();
            for (StudentEntity student : listStudent) {
                StudentResponse studentResponse = new StudentResponse();
                studentResponse.setId(student.getId());
                studentResponse.setRollNumber(student.getRollNumber());
                studentResponse.setName(student.getName());
                studentResponse.setGender(student.getGender());
                studentResponse.setDob(student.getDob());
                studentResponse.setStatus(student.getStatus());
                studentResponse.setUserName(student.getUserName());
                studentRequestList.add(studentResponse);
            }
            return studentRequestList;
        } else {
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public boolean checkgraduateCondition(String token) throws ForbiddenException {
        String username = getUserName.getUsername(token);
        logger.info("===== receive student username {}, token to check graduate =====", username);
        if (checkRoleService.checkRole(token)) {
            List<String> bookBorrow = restTemplate.getBookBorrow(token);
            if (bookBorrow == null) {
                return true;
            } else {
                return false;
            }
        } else {
            logger.error("this user don't have Permision to check");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public String getBookBorrow(String token) throws ForbiddenException {
        String username = getUserName.getUsername(token);
        StudentEntity studentEntity= studentRepository.findByUserName(username);
        logger.info("===== receive student username {}, token to get book borrow =====", username);
        if (checkRoleService.checkRole(token)) {
            List<String> bookBorrow = restTemplate.getBookBorrow(username);
            return bookBorrow.toString();
        } else {
            logger.error("this user don't have Permision to get");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public List<StudentResponse> getListGraduateStudent(String token) {
        return null;
    }

    public List<StudentResponse> getListGraduateStudent(String token,String status) {
        if (checkRoleService.checkRole(token)) {
            List<StudentResponse> studentResponses = new ArrayList<>();
            return studentResponses;
        } else {
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    private boolean checkRollNumberDupliacte(String rollNumber) {
        StudentEntity studentEntity = studentRepository.findByRollNumber(rollNumber);
        if (studentEntity == null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkgraduateByUsername(String username,String token) throws ForbiddenException {
        logger.info("===== receive student username {}, token to check graduate =====", username);
        List<String> bookBorrow = restTemplate.getBookBorrow(token);
        if (bookBorrow == null) {
            return true;
        } else {
            return false;
        }
    }

}
