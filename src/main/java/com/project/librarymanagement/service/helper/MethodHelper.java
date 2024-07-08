package com.project.librarymanagement.service.helper;

import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.BadRequestException;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;

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
}
