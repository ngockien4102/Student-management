package com.library.repository;

import com.library.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {
    BookEntity findByNameAndAndAuthor(String name,String author);
    List<BookEntity> findAll();
}
