//package com.library;
//
//import com.library.dto.Request.BookRequest;
//import com.library.entity.BookEntity;
//import com.library.repository.BookRepository;
//import com.library.service.impl.BookService;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.stubbing.Answer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
////@Runwith(MockitoJUnitRunner.class)
//@SpringBootTest(classes = LibraryServiceApplication.class)
//@ActiveProfiles("jpa")
//public class TestBookService {
//
//    public final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWVubm4iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA5MS9hY2NvdW50cy9sb2dpbiIsImV4cCI6MTYzNjQ0MTk0OH0.1pUW8ogZBdjD5JKgXumarUwaB6z-kUTp4TH4yhg4jF4";
//
//    @Autowired
//    BookService bookService;
//
//    @MockBean
//    private BookRepository bookRepository;
//
//
//    @Test
//    public void testBookExist_False() {
//        boolean check = bookService.checkBookExist("doremon", "ngoc");
//        Assert.assertEquals(false, check);
//    }
//
//    @Test
//    public void testBookExist_True() {
//        boolean check = bookService.checkBookExist("doron", "ngoc");
//        Assert.assertEquals(true, check);
//    }
//
//    @Test
//    public void TestGetBookById_True() {
//        BookEntity bookEntity = bookService.getBookById(1L);
//        boolean check = false;
//        if (bookEntity != null) {
//            check = true;
//        }
//        Assert.assertEquals(true, check);
//    }
//
//    @Test
//    public void TestGetBookById_False() {
//        boolean check = false;
//        try {
//            BookEntity bookEntity = bookService.getBookById(10L);
//
//            if (bookEntity != null) {
//                check = true;
//            }
//        } catch (Exception e) {
//
//        }
//
//        Assert.assertEquals(false, check);
//    }
//
//    @Test
//    public void test() {
//        BookRequest bookRequest = new BookRequest();
//        bookRequest.setAuthor("BB");
//        bookRequest.setName("AA");
//        when(bookRepository.save(any(BookEntity.class))).thenAnswer(new Answer<BookEntity>() {
//            public BookEntity answer(InvocationOnMock invocation) throws Throwable {
//                BookEntity bookEntity = new BookEntity();
//                bookEntity.setId(1L);
//                bookEntity.setAuthor(bookRequest.getAuthor());
//                bookEntity.setName(bookRequest.getName());
//                return bookEntity;
//            }
//        });
//        bookService.add_book(bookRequest, token);
////        Assertions.assertEquals(bookRequest.getAuthor(), bookService.add_book(new BookRequest(), token));
//    }
//
//}
