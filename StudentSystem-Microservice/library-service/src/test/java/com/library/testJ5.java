package com.library;

import com.library.controller.LibraryController;
import com.library.dto.Request.AddBookRequest;
import com.library.dto.Request.BookRequest;
import com.library.entity.BookEntity;
import com.library.repository.BookRepository;
import com.library.service.impl.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = LibraryServiceApplication.class)
@ActiveProfiles("jpa")
public class testJ5 {

    @Autowired
    BookService bookService;

    @MockBean
    private BookRepository bookRepository;
    public final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWVubm4iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA5MS9hY2NvdW50cy9sb2dpbiIsImV4cCI6MTYzNjQ0MTk0OH0.1pUW8ogZBdjD5JKgXumarUwaB6z-kUTp4TH4yhg4jF4";

    @Autowired
    LibraryController libraryController;

    @Test
    public void testAddBook() {
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setAuthor("BB");
        bookRequest.setName("AA");
        when(bookRepository.save(any(BookEntity.class))).thenAnswer(new Answer<BookEntity>() {
            public BookEntity answer(InvocationOnMock invocation) throws Throwable {
                BookEntity bookEntity = new BookEntity();
                bookEntity.setAuthor(bookRequest.getAuthor());
                bookEntity.setName(bookRequest.getName());
                return bookEntity;
            }
        });
        String check = libraryController.addBook(bookRequest,token);
        assertEquals("add new book success",check);
    }

    @Test
    public void updateBook(){
        BookRequest bookRequest = new BookRequest();
        bookRequest.setId(2l);
        bookRequest.setAuthor("kien");
        when(bookRepository.save(any(BookEntity.class))).thenAnswer(new Answer<BookEntity>() {
            @Override
            public BookEntity answer(InvocationOnMock invocationOnMock) throws Throwable {
                BookEntity bookEntity = new BookEntity();
                bookEntity.setAuthor(bookRequest.getAuthor());
                bookEntity.setName(bookRequest.getName());
                return bookEntity;
            }
            BookEntity bookEntity = new BookEntity();
        });
    }


}
