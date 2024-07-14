package com.project.librarymanagement.service.helper;

import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.entity.enums.RoleType;
import com.project.librarymanagement.entity.user.Role;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.BadRequestException;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.payload.messages.ErrorMessages;
import com.project.librarymanagement.repository.business.AuthorRepository;
import com.project.librarymanagement.repository.business.BookRepository;
import com.project.librarymanagement.repository.business.PublisherRepository;
import com.project.librarymanagement.repository.user.RoleRepository;
import com.project.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final RoleRepository roleRepository;

    public User loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public Role findRoleByName(String roleName){
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
    }

    public boolean checkUserIfMember(User user){
        List<Role> roles = user.getRoles().stream().toList();

        return roles.contains(RoleType.MEMBER);
    }

    public void checkBuildIn(User user) {
        if (user.getBuiltIn()) {
            throw new BadRequestException("You do not have any permission to do this operation");
        }
    }

    public Book isBookExist(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_BOOK_MESSAGE, id)));
    }

public Author isAuthorExist(Long id){
        return authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_AUTHOR_MESSAGE,id)));
}

    public Publisher getPublisherById(Long id){
        return publisherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_PUBLISHER_MESSAGE,id)));
    }

    public boolean isPublisherExist(Long id) {
        if (publisherRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_PUBLISHER_MESSAGE,id));
        }

        return true;
    }

    public void isPublisherExistByName(String name) {
        if (!publisherRepository.existsByName(name)) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_PUBLISHER_MESSAGE_NAME, name));
        }
    }

}
