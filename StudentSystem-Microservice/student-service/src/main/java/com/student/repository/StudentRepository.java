package com.student.repository;

import com.student.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    StudentEntity findByRollNumber(String rollNumber);
    StudentEntity findByUserName(String username);
}
