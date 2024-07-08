package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.payload.mapper.BookMapper;
import com.project.librarymanagement.payload.messages.SuccessMessages;
import com.project.librarymanagement.payload.request.business.BookRequest;
import com.project.librarymanagement.payload.response.business.BookResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.repository.business.BookRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final MethodHelper methodHelper;


    public ResponseMessage<BookResponse> saveBook(BookRequest bookRequest) {
        //get author from author service
        Author author = null;
        //get publisher from publisher service
        Publisher publisher = null;
        //get category from category service
        Category category = null;

        //map DTO to entity
        Book book = bookMapper.mapBookRequestToBook(bookRequest, author, publisher, category);
        Book savedBook = bookRepository.save(book);
        return ResponseMessage.<BookResponse>builder()
                .returnBody(bookMapper.mapBookToBookResponse(savedBook))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.BOOK_SAVE)
                .build();
    }

    public BookResponse getById(Long id) {
        return bookMapper.mapBookToBookResponse(methodHelper.isBookExist(id));
    }
}
