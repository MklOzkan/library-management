package com.project.librarymanagement.payload.mapper;

import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.payload.request.business.BookRequest;
import com.project.librarymanagement.payload.response.business.BookResponse;

import lombok.Data;
import org.springframework.stereotype.Component;



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
}


