package com.project.librarymanagement.payload.mapper;

import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.payload.request.business.BookRequest;
import com.project.librarymanagement.payload.request.business.BookUpdateRequest;
import com.project.librarymanagement.payload.response.business.BookResponse;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;


@Data
@Component
public class BookMapper {

    public Book mapBookRequestToBook(BookRequest bookRequest, Author author, Publisher publisher, Category category) {
        return Book.builder()
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .author(author)
                .publisher(publisher)
                .publishDate(bookRequest.getPublishDate())
                .category(category)
                .image(bookRequest.getImage())
                .loanable(bookRequest.getLoanable())
                .shelfCode(bookRequest.getShelfCode())
                .active(bookRequest.getActive())
                .featured(bookRequest.getFeatured())
                .createDate(bookRequest.getCreateDate())
                .builtIn(bookRequest.getBuiltIn())
                .build();
    }

    public BookResponse mapBookToBookResponse(Book book) {
        return BookResponse.builder()
                .name(book.getName())
                .isbn(book.getIsbn())
                .pageCount(book.getPageCount())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publishDate(book.getPublishDate())
                .category(book.getCategory())
                .image(book.getImage())
                .loanable(book.getLoanable())
                .shelfCode(book.getShelfCode())
                .active(book.getActive())
                .featured(book.getFeatured())
                .createDate(book.getCreateDate())
                .builtIn(book.getBuiltIn())
                .build();


    }

    public Book mapBookUpdateRequestToBook(BookUpdateRequest bookUpdateRequest, Long bookId, Author author, Publisher publisher, Category category){
        return Book.builder()
                .id(bookId)
                .name(bookUpdateRequest.getName())
                .isbn(bookUpdateRequest.getIsbn())
                .pageCount(bookUpdateRequest.getPageCount())
                .author(author)
                .publisher(publisher)
                .category(category)
                .publishDate(bookUpdateRequest.getPublishDate())
                .image(bookUpdateRequest.getImage())
                .shelfCode(bookUpdateRequest.getShelfCode())
                .active(bookUpdateRequest.getActive())
                .featured(bookUpdateRequest.getFeatured())
                .build();



    }
}



