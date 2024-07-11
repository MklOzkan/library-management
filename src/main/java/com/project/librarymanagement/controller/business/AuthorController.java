package com.project.librarymanagement.controller.business;

import com.project.librarymanagement.payload.request.business.AuthorRequest;
import com.project.librarymanagement.payload.response.business.AuthorResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.service.business.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor

public class AuthorController {

    private final AuthorService authorService;
    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/save")
    public ResponseMessage<AuthorResponse> saveAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
        return authorService.saveAuthor(authorRequest);
    }
    @GetMapping("/findAuthorsByPage")
    public Page<AuthorResponse> findAuthorsByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type) {
        return authorService.findAuthorsByPage(page, size, sort, type);

    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/update/{authorId}")
    public ResponseMessage<AuthorResponse>updateAuthorById(@PathVariable Long id,
                                                          @RequestBody @Valid AuthorRequest authorRequest){
        return authorService.updateAuthor(id,authorRequest);
    }
    @PreAuthorize("hasAnyAuthority('Admin')")
    @DeleteMapping("/delete/{authorId}")
    public ResponseMessage deleteAuthorById(@PathVariable Long id) {

        return authorService.deleteAuthorById(id);
    }

}
