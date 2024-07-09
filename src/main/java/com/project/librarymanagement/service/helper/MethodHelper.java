package com.project.librarymanagement.service.helper;

import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.BadRequestException;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.payload.messages.ErrorMessages;
import com.project.librarymanagement.repository.business.BookRepository;
import com.project.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public User loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public void checkBuildIn(User user){
        if(user.getBuiltIn()){
            throw new BadRequestException("You do not have any permission to do this operation");
        }
    }

    public Book isBookExist(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_BOOK_MESSAGE, id)));
    }
}
