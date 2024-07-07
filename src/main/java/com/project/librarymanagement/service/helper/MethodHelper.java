package com.project.librarymanagement.service.helper;

import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}
