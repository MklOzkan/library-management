package com.project.librarymanagement.payload.mapper;

import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.payload.request.business.AuthorRequest;
import com.project.librarymanagement.payload.response.business.AuthorResponse;
import lombok.Data;
import org.springframework.stereotype.Component;
@Data
@Component
public class AuthorMapper {

    public Author mapAuthorRequestToAuthor(AuthorRequest authorRequest){
        return Author.builder()
                .name(authorRequest.getName())
                .builtIn(authorRequest.getBuiltIn()).build();
    }
    public AuthorResponse mapAuthorToAuthorResponse(Author author){
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .builtIn(author.getBuiltIn())
                .build();
    }
}



