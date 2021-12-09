package com.library.repository;

import com.library.entity.BookManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface BookmanagementRepository extends JpaRepository<BookManagementEntity, Long> {


//    BookManagementEntity findByStudentCodeaAndBookEntityId(String studentCode);

    BookManagementEntity findByUsernameAndBookEntity_Id(String studentCode, Long bookId);

    @Query(nativeQuery = true, value = "select * from bookmanagement where user_name = :name")
    ArrayList<BookManagementEntity> findAllByStudentCode(@Param("name") String username);

    @Query(nativeQuery = true, value = "select name from book join bookmanagement on book.id = bookmanagement.book_id where bookmanagement.status = 'BORROW' \n" +
            "and bookmanagement.user_name = :name")
    List<String> getBookBorrow(@Param("name") String username);

}
