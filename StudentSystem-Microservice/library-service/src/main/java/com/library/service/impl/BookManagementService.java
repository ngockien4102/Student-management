package com.library.service.impl;

import com.library.RestTemplate.RestTemplateService;
import com.library.decode.CheckRoleService;
import com.library.decode.GetUserName;
import com.library.dto.Enum.BookStatus;
import com.library.dto.Request.BookRegisterRequest;
import com.library.dto.Response.ExceptionResponse;
import com.library.entity.BookEntity;
import com.library.entity.BookManagementEntity;
import com.library.exception.*;
import com.library.repository.BookRepository;
import com.library.repository.BookmanagementRepository;
import com.library.service.IBook;
import com.library.service.IBookManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookManagementService implements IBookManagement {

    private static final Logger logger = LoggerFactory.getLogger(BookManagementService.class);

    @Autowired
    BookmanagementRepository bookmanagementRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IBook iBook;

    @Autowired
    CheckRoleService checkRoleService;

    @Autowired
    RestTemplateService restTemplateService;

    @Autowired
    GetUserName getName;


    private void register(String username, Long bookId) throws BadRequestException, QuantityException {
        logger.info("===== Receive studentCode {}, bookId {} to register =====", username, bookId);
        BookEntity bookManagement = bookRepository.findById(bookId).orElseThrow(() -> new BadRequestException(new ExceptionResponse(ErrorCode.notFound)));
        if (bookManagement.getQuantity() > 0) {
            BookEntity book = new BookEntity();
            book.setId(bookId);
            BookManagementEntity bookManagementEntity = new BookManagementEntity();
            bookManagementEntity.setStatus(String.valueOf(BookStatus.REGISTER));
            bookManagementEntity.setUsername(username);
            bookManagementEntity.setBookEntity(book);
            bookmanagementRepository.save(bookManagementEntity);
//            logger.info("===== finish =====");
        } else {
            logger.error("book quantity was exhausted");
            throw new QuantityException(new ExceptionResponse(ErrorCode.notEnoughBook));
        }
    }

    @Override
    public String RegisterBook(BookRegisterRequest bookRegisterRequest, String token) throws ForbiddenException {
        bookRegisterRequest.setUsername(getName.getUsername(token));
        if (checkRoleService.checkRole(token)) {
            String name = bookRegisterRequest.getUsername();
            for (Long id : bookRegisterRequest.getBookId()) {
                BookEntity bookEntity = iBook.getBookById(id);
                if (bookEntity != null && checkExist(name, id, "REGISTER") ) {
                    if(checkExist(name, id, "BORROW")){
                        register(name, id);
                    }else {
                        throw new RegisterBookException(new ExceptionResponse(ErrorCode.registerBook));
                    }
                } else {
                    logger.error("this user was register this book");
                    throw new DuplicateException(new ExceptionResponse(ErrorCode.DuplicateRequest));
                }
            }
        } else {
            logger.error("this user don't have permision to register book");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
        logger.info("===== finish register book =====");
        return "";
    }

    @Override
    public String borrow(BookRegisterRequest bookRegisterRequest, String token) throws BadRequestException, ForbiddenException {
        bookRegisterRequest.setUsername(getName.getUsername(token));
        if (checkRoleService.checkRole(token)) {
            String name = bookRegisterRequest.getUsername();
            for (Long bookId : bookRegisterRequest.getBookId()) {
                logger.info("Receive studentCode {}, bookId {} to borrow", name, bookId);
                BookManagementEntity bookManagementEntity = bookmanagementRepository.findByUsernameAndBookEntity_Id(name, bookId);
                if (bookManagementEntity != null && bookManagementEntity.getStatus().equals("REGISTER")) {
                    bookManagementEntity.setStatus(String.valueOf(BookStatus.BORROW));
                    updateQuantity(bookId,BookStatus.BORROW);
                    bookmanagementRepository.save(bookManagementEntity);
                } else {
                    logger.error("this book don't register borrow");
                    throw new BorowBookException(new ExceptionResponse(ErrorCode.borowBook));
                }
            }

        } else {
            logger.error("this user don't have permision to borrow book");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
        logger.info("===== finish borrow book =====");
        return "borrow success!!!";
    }


    private void returnBook(String username, Long bookId) throws ForbiddenException {
        logger.info("Receive studentCode {}, bookId {} to return book", username, bookId);
        BookManagementEntity bookManagementEntity = bookmanagementRepository.findByUsernameAndBookEntity_Id(username, bookId);
        if (bookManagementEntity != null) {
            bookManagementEntity.setStatus(String.valueOf(BookStatus.RETURN));
            bookmanagementRepository.save(bookManagementEntity);
//            logger.info("===== finish =====");
        } else {
            logger.error("this user don't have permision to return book");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public String BookReturn(BookRegisterRequest bookRegisterRequest, String token) throws ForbiddenException {
        bookRegisterRequest.setUsername(getName.getUsername(token));
        if (checkRoleService.checkRole(token)) {
            String name = bookRegisterRequest.getUsername();
            for (Long id : bookRegisterRequest.getBookId()) {
                updateQuantity(id,BookStatus.RETURN);
                returnBook(name, id);
            }
            logger.info("===== finish return book =====");
            return "return success!!!";
        } else {
            logger.error("this user don't have permision to return book");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    private void unRegister(String username, Long bookId) throws BadRequestException {
        logger.info("Receive studentCode {}, bookId {} to unregister", username, bookId);
        BookManagementEntity bookManagement = bookmanagementRepository.findByUsernameAndBookEntity_Id(username, bookId);

        bookManagement.setStatus(String.valueOf(BookStatus.UNREGISTER));
        bookmanagementRepository.save(bookManagement);
//        logger.info("===== finish  =====");
    }

    @Override
    public String UnRegisterBook(BookRegisterRequest bookRegisterRequest, String token) throws ForbiddenException {
        bookRegisterRequest.setUsername(getName.getUsername(token));
        if (checkRoleService.checkRole(token)) {
            String name = bookRegisterRequest.getUsername();
            for (Long id : bookRegisterRequest.getBookId()) {
                BookEntity bookEntity = iBook.getBookById(id);
                if (bookEntity != null && checkExist(name, id, "UNREGISTER")) {
                    unRegister(name, id);
                } else {
                    logger.error("this request is duplicate");
                    throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
                }
            }
        } else {
            logger.error("this user don't have permision to unregister book");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
        logger.info("===== finish unregister book =====");
        return "";
    }

    @Override
    public List<String> getBorrowBook(String username) throws ForbiddenException {
        List<String> bookBorrow = bookmanagementRepository.getBookBorrow(username);
        return bookBorrow;
    }

    @Override
    public boolean checkExist(String username, Long bookId, String status) {
        logger.info("===== receive username {}, bookId {}, status {} to check request exist =====", username, bookId, status);
        BookManagementEntity bookManagementEntity = bookmanagementRepository.findByUsernameAndBookEntity_Id(username, bookId);
        if (bookManagementEntity == null || !bookManagementEntity.getStatus().equals(status)) {
            logger.info("===== finish check exist is true =====");
            return true;
        } else {
            logger.info("===== finish check exist is false =====");
            return false;
        }
    }

    public void updateQuantity(Long bookId, BookStatus bookStatus) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new BadRequestException(new ExceptionResponse(ErrorCode.notFound)));
        int quantity = bookEntity.getQuantity();
        if (bookStatus.equals(BookStatus.BORROW)) {
            bookEntity.setQuantity(quantity-1);
        } else if(bookStatus.equals(BookStatus.RETURN)){
            bookEntity.setQuantity(quantity+1);
        }

            bookRepository.save(bookEntity);
    }
}
