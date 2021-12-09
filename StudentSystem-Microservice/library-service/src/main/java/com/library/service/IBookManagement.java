package com.library.service;

import com.library.dto.Request.BookRegisterRequest;

import javax.validation.Valid;
import java.util.List;

public interface IBookManagement {
    String RegisterBook(BookRegisterRequest bookRegisterRequest,String token) ;

    String borrow(BookRegisterRequest bookRegisterRequest,String token);

    boolean checkExist(String studentCode, Long bookId,String status);

    String BookReturn(BookRegisterRequest bookRegisterRequest, String token);

    String UnRegisterBook(BookRegisterRequest bookRegisterRequest, String token);

    List<String> getBorrowBook( String username);
}
