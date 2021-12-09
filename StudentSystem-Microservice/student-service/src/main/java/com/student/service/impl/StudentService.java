package com.student.service.impl;

import com.student.Token.CheckRoleService;
import com.student.dto.Request.StudentRequest;
import com.student.dto.Response.ExceptionResponse;
import com.student.entity.StudentEntity;
import com.student.exception.BadRequestException;
import com.student.exception.DuplicateRollnumberException;
import com.student.exception.ErrorCode;
import com.student.exception.ForbiddenException;
import com.student.mapper.StudentMapper;
import com.student.repository.StudentRepository;
import com.student.restTemplate.RestTemplateService;
import com.student.service.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    CheckRoleService checkRoleService;

    @Autowired
    RestTemplateService restTemplate;

    final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Override
    public StudentEntity getStudentById(long id, String token) throws BadRequestException, ForbiddenException {
        logger.info("===== receive student id {}, token to get student by id =====",id);
        if (checkRoleService.checkRole(token)) {
            StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new BadRequestException(new ExceptionResponse(ErrorCode.notFound)));
            return studentEntity;

        } else {
            logger.error("this user don't have Permision to get");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public StudentRequest updateStudent(StudentRequest studentDto, String token)throws ForbiddenException,BadRequestException {
        logger.info("===== receive student info {}, token to update =====",studentDto.toString());
        if (checkRoleService.checkRole(token)) {
            StudentEntity student = student = studentRepository.findById(studentDto.getId()).orElseThrow(() -> new BadRequestException(new ExceptionResponse(ErrorCode.notFound)));
            student.setRollNumber(studentDto.getRollNumber());
            student.setName(studentDto.getName());
            student.setGender(studentDto.isGender());
            student.setDob(studentDto.getDob());
            student.setStatus(studentDto.getStatus());
            studentRepository.save(student);
            return studentDto;
        } else {
            logger.error("this user don't have Permision to update");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public StudentRequest insertStudent(StudentRequest studentDto, String token) throws ForbiddenException, DuplicateRollnumberException {
        logger.info("===== receive student info {}, token to insert =====",studentDto.toString());
        if (checkRoleService.checkRole(token)) {
            if (checkRollNumberDupliacte(studentDto.getRollNumber())) {
                StudentEntity student = new StudentEntity();
                student.setRollNumber(studentDto.getRollNumber());
                student.setName(studentDto.getName());
                student.setGender(studentDto.isGender());
                student.setDob(studentDto.getDob());
                student.setStatus(studentDto.getStatus());
                studentRepository.save(student);
                return studentDto;
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
    public List<StudentRequest> getListStudent() {
        List<StudentEntity> listStudent = studentRepository.findAll();
        return studentMapper.toListStudentDto(listStudent);
    }



    @Override
    public String checkgraduateCondition(String username,String token) throws ForbiddenException{
        logger.info("===== receive student username {}, token to check graduate =====",username);
        if(checkRoleService.checkRole(token)){
            List<String> bookBorrow = restTemplate.getBookBorrow(username);
            if(bookBorrow==null){
                return "this student can graduate";
            }else {
                return "this student can't graduate";
            }
        }else {
            logger.error("this user don't have Permision to check");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public List<String> getBookBorrow(String username, String token) throws ForbiddenException{
        logger.info("===== receive student username {}, token to get book borrow =====",username);
        if(checkRoleService.checkRole(token)){
            List<String> bookBorrow = restTemplate.getBookBorrow(username);
            return bookBorrow;
        }else {
            logger.error("this user don't have Permision to get");
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

}
