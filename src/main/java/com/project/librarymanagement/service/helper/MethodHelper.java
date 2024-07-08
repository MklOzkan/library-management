package com.project.librarymanagement.service.helper;

import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.user.User;
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
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public Book isBookExist(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_BOOK_MESSAGE, id)));
    }
}
