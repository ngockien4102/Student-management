//package com.student.mapper;
//
//import com.student.dto.Request.StudentRequest;
//import com.student.entity.StudentEntity;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class StudentMapper {
//    public static StudentRequest toStudentDto(StudentEntity student) {
//        StudentRequest studentConverter = new StudentRequest();
//        studentConverter.setId(student.getId());
//        studentConverter.setRollNumber(student.getRollNumber());
//        studentConverter.setName(student.getName());
//        studentConverter.setGender(student.isGender());
//        studentConverter.setDob(student.getDob());
//        studentConverter.setStatus(student.getStatus());
//        return studentConverter;
//    }
//
//    public List<StudentRequest> toListStudentDto (List<StudentEntity> students){
//        List<StudentRequest> list = new ArrayList<>();
//        for(StudentEntity student:students){
//            StudentRequest studentConverter = new StudentRequest();
//            studentConverter.setId((student.getId()));
//            studentConverter.setRollNumber(student.getRollNumber());
//            studentConverter.setName(student.getName());
//            studentConverter.setGender(student.isGender());
//            studentConverter.setDob(student.getDob());
//            studentConverter.setStatus(student.getStatus());
//            list.add(studentConverter);
//        }
//        return list;
//    }
//}
