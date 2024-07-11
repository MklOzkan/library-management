package com.project.librarymanagement.controller.business;


import com.project.librarymanagement.payload.request.business.BookRequest;
import com.project.librarymanagement.payload.request.business.BookUpdateRequest;
import com.project.librarymanagement.payload.response.business.BookResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.service.business.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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




    @GetMapping("/findBookByPage")
    public Page<BookResponse> findBookByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type){
        return bookService.findBookByPage(page, size, sort, type);
    }


    @GetMapping("/getById/{id}")
    public BookResponse getById(@PathVariable Long id) {
        return bookService.getById(id);
    }


    @PutMapping("/update/{bookId}")
    public ResponseMessage<BookResponse> updateById(@PathVariable Long bookId, @RequestBody @Valid BookUpdateRequest bookUpdateRequest){
        return bookService.updateBook(bookId, bookUpdateRequest);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseMessage<Object> deleteById(@PathVariable Long bookId){
        return bookService.deleteById(bookId);
    }


}
