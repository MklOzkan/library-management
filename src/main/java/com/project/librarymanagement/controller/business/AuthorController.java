package com.project.librarymanagement.controller.business;

import com.project.librarymanagement.payload.request.business.AuthorRequest;
import com.project.librarymanagement.payload.response.business.AuthorResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.service.business.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor

public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/save")
    public ResponseMessage<AuthorResponse> saveAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
        return authorService.saveAuthor(authorRequest);
    }


}
