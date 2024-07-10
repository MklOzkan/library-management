package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.payload.mapper.AuthorMapper;
import com.project.librarymanagement.payload.messages.SuccessMessages;
import com.project.librarymanagement.payload.request.business.AuthorRequest;
import com.project.librarymanagement.payload.response.business.AuthorResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.repository.business.AuthorRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;
    private final AuthorMapper authorMapper;


    public ResponseMessage<AuthorResponse> saveAuthor(AuthorRequest authorRequest) {
        // map from DTO -> entity
        Author author = authorMapper.mapAuthorRequestToAuthor(authorRequest);
        Author savedAuthor = authorRepository.save(author);
        return ResponseMessage.<AuthorResponse>builder()
                .returnBody(authorMapper.mapAuthorToAuthorResponse(savedAuthor))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.AUTHOR_SAVE)
                .build();
    }



}

