package com.library.controller;

import com.library.RestTemplate.RestTemplateService;
import com.library.dto.Request.AddBookRequest;
import com.library.dto.Request.BookRequest;
import com.library.dto.Response.ExceptionResponse;
import com.library.dto.Request.BookRegisterRequest;
import com.library.exception.BadRequestException;
import com.library.exception.ForbiddenException;
import com.library.service.IBook;
import com.library.service.IBookManagement;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class LibraryController {

    @Autowired
    IBook iBook;

    @Autowired
    RestTemplateService restTemplateService;

    @Autowired
    IBookManagement iBookManagement;

    //API add new book
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)
    })
    @PostMapping("/int/book")
    public String addBook(@Valid @RequestBody AddBookRequest bookRequest, @RequestHeader("AUTHORIZATION") String token) throws ForbiddenException {
        iBook.add_book(bookRequest, token);
        return "add new book success";
    }


    //API get All book
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class)
    })
    @GetMapping("/ext/book")
    public List<BookRequest> getBook() {
        return iBook.get_book();
    }


    //API update book
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)
    })
    @PutMapping("/ext/book")
    public String updateBook(@Valid @RequestBody BookRequest bookRequest, @RequestHeader("AUTHORIZATION") String token) throws ForbiddenException {
        iBook.update_book(bookRequest, token);
        return "update book success";
    }

    //API delete book
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)
    })
    @DeleteMapping("/ext/book/{id}")
    public String deleteBook(@Valid @PathVariable("id") Long id, @RequestHeader("AUTHORIZATION") String token) throws ForbiddenException {
        iBook.delete_book(id, token);
        return "delete book success";
    }

    //API register to borrow book
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)
    })
    @PostMapping("/ext/book/registerbook")
    public String registerBook(@Valid @RequestBody BookRegisterRequest bookRegisterRequest,@RequestHeader("AUTHORIZATION")String token) throws ForbiddenException {
        iBookManagement.RegisterBook(bookRegisterRequest,token);
        return "register borrow book success";
    }

    //API unregister to borrow book register before
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)
    })
    @PutMapping("int/book/unregisterbook")
    public String unRegisterBook(@Valid @RequestBody BookRegisterRequest bookRegisterRequest,@RequestHeader("AUTHORIZATION")String token) throws ForbiddenException {
        iBookManagement.UnRegisterBook(bookRegisterRequest,token);
        return "unregister book borrow success";
    }

    //API to actually borrow book
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)
    })
    @PutMapping("/int/book/borrow")
    public String borrowBook(@Valid @RequestBody BookRegisterRequest bookRegisterRequest, @RequestHeader("AUTHORIZATION") String token) throws ForbiddenException {
        iBookManagement.borrow(bookRegisterRequest, token);
        return "borrow book susscess";
    }

    //API to return book
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)
    })
    @PutMapping("/int/book/returnbook")
    public String returnBook(@Valid @RequestBody BookRegisterRequest bookRegisterRequest, @RequestHeader("AUTHORIZATION") String token) throws ForbiddenException {
        iBookManagement.BookReturn(bookRegisterRequest, token);
        return "return book success";
    }


    //API to get book borrow by username
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 500, message = "Failure", response = ExceptionResponse.class)
    })
    @GetMapping("int/book/getBorrowBook/{username}")
    public List<String> getBookBorrow(@PathVariable("username")String username){
        List<String> bookBorrow = iBookManagement.getBorrowBook(username);
        return bookBorrow;
    }

}