package com.library.service;


import com.library.dto.Request.AddBookRequest;
import com.library.dto.Request.BookRequest;
import com.library.entity.BookEntity;

import java.util.List;

public interface IBook {
    void add_book(AddBookRequest addBookRequest, String token);

    BookRequest update_book(BookRequest bookDto, String token);

    void delete_book(Long id,String token);

    List<BookRequest> get_book();

    BookEntity getBookById(Long bookId);
}
