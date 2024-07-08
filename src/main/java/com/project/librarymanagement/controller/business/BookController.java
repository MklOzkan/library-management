package com.project.librarymanagement.controller.business;


import com.project.librarymanagement.payload.request.business.BookRequest;
import com.project.librarymanagement.payload.response.business.BookResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.service.business.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/save")
    public ResponseMessage<BookResponse>saveBook(@RequestBody @Valid BookRequest bookRequest){
        return bookService.saveBook(bookRequest);
    }


    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable Long id){
       return bookService.getById(id);
    }




}
