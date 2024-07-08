package com.project.librarymanagement.controller.business;

import com.project.librarymanagement.service.business.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor

public class AuthorController {

    private final AuthorService authorService;



}
