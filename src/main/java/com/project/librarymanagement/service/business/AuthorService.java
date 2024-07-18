package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.ConflictException;
import com.project.librarymanagement.payload.mapper.AuthorMapper;
import com.project.librarymanagement.payload.messages.ErrorMessages;
import com.project.librarymanagement.payload.messages.SuccessMessages;
import com.project.librarymanagement.payload.request.business.AuthorRequest;
import com.project.librarymanagement.payload.response.business.AuthorResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.repository.business.AuthorRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.service.helper.PageableHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;
    private final AuthorMapper authorMapper;


    public ResponseMessage<AuthorResponse> saveAuthor(AuthorRequest authorRequest, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.loadUserByEmail(email);
        methodHelper.isRoleAdmin(user);
        isAuthorExistByName(authorRequest.getName());
        // map from DTO -> entity
        Author author = authorMapper.mapAuthorRequestToAuthor(authorRequest);

        Author savedAuthor = authorRepository.save(author);
        return ResponseMessage.<AuthorResponse>builder()
                .returnBody(authorMapper.mapAuthorToAuthorResponse(savedAuthor))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.AUTHOR_SAVE)
                .build();
    }
    private void isAuthorExistByName(String name) {
        if (authorRepository.getByNameEqualsIgnoreCase(name).isPresent()) {
            throw new ConflictException(
                    String.format(ErrorMessages.ALREADY_CREATED_AUTHOR_MESSAGE, name));
        }
    }

    public Page<AuthorResponse> findAuthorsByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return authorRepository.findAll(pageable)
                .map(authorMapper::mapAuthorToAuthorResponse);
    }


    public ResponseMessage<AuthorResponse> updateAuthor(Long id, AuthorRequest authorRequest, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.loadUserByEmail(email);
        methodHelper.isRoleAdmin(user);
        //validate if author exists
     Author author =methodHelper.isAuthorExist(id);
        // Update the existing author entity
        if(!author.getName().equals(authorRequest.getName())){
           isAuthorExistByName(authorRequest.getName());
        }
        Author updatedAuthor = authorMapper.mapAuthorRequestToAuthor(authorRequest);
        updatedAuthor.setId(id);
        // Save the updated author
        Author savedAuthor = authorRepository.save(updatedAuthor);
        //map from entity to DTO
        return ResponseMessage.<AuthorResponse>builder()
                .message(SuccessMessages.AUTHOR_UPDATE)
                .returnBody(authorMapper.mapAuthorToAuthorResponse(savedAuthor))
                .httpStatus(HttpStatus.OK)
                .build();
    }


    public ResponseMessage deleteAuthorById(Long id) {
        methodHelper.isAuthorExist(id);
        authorRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(SuccessMessages.AUTHOR_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }


    public AuthorResponse findAuthorById(Long id) {
        Author author=methodHelper.isAuthorExist(id);
        return authorMapper.mapAuthorToAuthorResponse(author);
    }


}





