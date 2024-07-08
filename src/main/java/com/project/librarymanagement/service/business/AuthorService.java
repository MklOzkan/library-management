package com.project.librarymanagement.service.business;

import com.project.librarymanagement.repository.business.AuthorRepository;
import com.project.librarymanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PageableHelper pageableHelper;



}

