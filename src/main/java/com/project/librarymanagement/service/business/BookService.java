package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.payload.mapper.BookMapper;
import com.project.librarymanagement.payload.messages.ErrorMessages;
import com.project.librarymanagement.payload.messages.SuccessMessages;
import com.project.librarymanagement.payload.request.business.BookRequest;
import com.project.librarymanagement.payload.request.business.BookUpdateRequest;
import com.project.librarymanagement.payload.response.business.BookResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.repository.business.BookRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.service.helper.PageableHelper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;


    public ResponseMessage<BookResponse> saveBook(BookRequest bookRequest) {
        //get author from author service
        Author author = null;  //<=============================================================== needs to be changed after author part is done
        //get publisher from publisher service
        Publisher publisher = null; //<=============================================================== needs to be changed after publisher part is done
        //get category from category service
        Category category = null; //<=============================================================== needs to be changed after category part is done

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



    public Page<BookResponse> findBookByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return bookRepository.findAll(pageable).map(bookMapper::mapBookToBookResponse);
    }

Book isBookExistById(Long id){
        return bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_BOOK_MESSAGE,id)));
}


    public ResponseMessage<BookResponse> updateBook(Long bookId, BookUpdateRequest bookUpdateRequest) {

        //validate if author exists
        Author author = null; //<=============================================================== need to be changed after author part is done

        //validate if publisher exists
        Publisher publisher = null; //<=============================================================== need to be changed after publisher part is done


        //validate if category exists
        Category category = null;   //<=============================================================== need to be changed after category part is done


        //validate if book exists
        Book bookFromDB = isBookExistById(bookId);


        Book bookToUpdate = bookMapper.mapBookUpdateRequestToBook(bookUpdateRequest, bookId, author, publisher, category);

        Book updatedBook = bookRepository.save(bookToUpdate);

        return ResponseMessage.<BookResponse>builder()
                .message(SuccessMessages.BOOK_UPDATE)
                .returnBody(bookMapper.mapBookToBookResponse(updatedBook))
                .build();




    }

    public ResponseMessage<Object> deleteById(Long bookId) {
        Object book = isBookExistById(bookId);

         bookRepository.deleteById(bookId);

         return  ResponseMessage.<Object>builder()
                 .message(SuccessMessages.BOOK_DELETE)
                 .returnBody(book)
                 .build();
    }
}
