package com.library.service.impl;

import com.library.decode.GetUserName;
import com.library.dto.Enum.BookStatus;
import com.library.dto.Request.AddBookRequest;
import com.library.exception.BadRequestException;
import com.library.exception.DuplicateException;
import com.library.exception.ErrorCode;
import com.library.dto.Response.ExceptionResponse;
import com.library.dto.Request.BookRequest;
import com.library.entity.BookEntity;
import com.library.exception.ForbiddenException;
import com.library.repository.BookRepository;
import com.library.decode.CheckRoleService;
import com.library.service.IBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService implements IBook {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CheckRoleService checkRoleService;

    @Autowired
    GetUserName getUserName;

    final static Logger logger = LoggerFactory.getLogger(BookService.class);

    @Override
    public void add_book(AddBookRequest addBookRequest, String token) throws ForbiddenException {
        logger.info("receive info of book {} to add new book", addBookRequest.getName());
        if (checkRoleService.checkRole(token)) {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setName(addBookRequest.getName());
            bookEntity.setAuthor(addBookRequest.getAuthor());
            bookEntity.setDescription(addBookRequest.getDescription());
            bookEntity.setPageNumber(addBookRequest.getPageNumber());
            bookEntity.setPublishDate(addBookRequest.getPublishDate());
            bookEntity.setImage(addBookRequest.getImage());
            bookEntity.setCategory(addBookRequest.getCategory());
            bookEntity.setQuantity(addBookRequest.getQuantity());
            bookEntity.setBookStatus(String.valueOf(BookStatus.ACTIVE));

            if (checkBookExist(bookEntity.getName(), bookEntity.getAuthor())) {
                bookRepository.save(bookEntity);
                logger.info("===== finish add new book =====");
            } else {
                    throw new DuplicateException(new ExceptionResponse(ErrorCode.DuplicateRequest));
            }
        } else {
            logger.error("this user don't have Permision to add new book");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public BookRequest update_book(BookRequest bookRequest, String token) throws ForbiddenException {
        logger.info("receive info of book {} to update", bookRequest.getName());
        if (checkRoleService.checkRole(token)) {
            BookEntity bookEntity = bookRepository.findById(bookRequest.getId()).orElseThrow(() -> new BadRequestException(new ExceptionResponse(ErrorCode.notFound)));
            bookEntity.setName(bookRequest.getName());
            bookEntity.setAuthor(bookRequest.getAuthor());
            bookEntity.setDescription(bookRequest.getDescription());
            bookEntity.setPageNumber(bookRequest.getPageNumber());
            bookEntity.setPublishDate(bookRequest.getPublishDate());
            bookEntity.setImage(bookRequest.getImage());
            bookEntity.setCategory(bookRequest.getCategory());
            bookEntity.setBookStatus(bookRequest.getBookStatus());
            bookRepository.save(bookEntity);
            logger.info("===== finish update book =====");
            return bookRequest;
        } else {
            logger.error("this user don't have Permision to update book");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notFound));
        }
    }

    @Override
    public void delete_book(Long id, String token) throws ForbiddenException {
        if (checkRoleService.checkRole(token)) {
            BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() -> new BadRequestException(new ExceptionResponse(ErrorCode.notFound)));
            logger.info("receive request delete book {} ", bookEntity.getName());
            if (!bookEntity.getBookStatus().equals("INACTIVE")) {
                bookEntity.setBookStatus(String.valueOf(BookStatus.INACTIVE));
                bookRepository.save(bookEntity);
                logger.info("===== finish delete book =====");
            }
        } else {
            logger.error("this user don't have Permision to delete book");
            throw new ForbiddenException(new ExceptionResponse(ErrorCode.notPermision));
        }
    }

    @Override
    public List<BookRequest> get_book() {
        BookEntity student = new BookEntity();
        List<BookEntity> bookEntityList = bookRepository.findAll();
        List<BookRequest> list = new ArrayList<>();
        for (BookEntity book : bookEntityList) {
            BookRequest bookDto = new BookRequest();
            bookDto.setId(book.getId());
            bookDto.setName(book.getName());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setDescription(book.getDescription());
            bookDto.setPageNumber(book.getPageNumber());
            bookDto.setPublishDate(book.getPublishDate());
            bookDto.setImage(book.getImage());
            bookDto.setCategory(book.getCategory());
            bookDto.setBookStatus(book.getBookStatus());
            bookDto.setQuantity(book.getQuantity());
            list.add(bookDto);
        }
        return list;
    }

    @Override
    public BookEntity getBookById(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new BadRequestException(new ExceptionResponse(ErrorCode.notFound)));
        return bookEntity;
    }

    public boolean checkBookExist(String name, String author) {
        BookEntity bookEntity = bookRepository.findByNameAndAndAuthor(name, author);
        if (bookEntity != null) {
            return false;
        } else {
            return true;
        }
    }
}
