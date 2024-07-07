package com.project.librarymanagement.security.service;

import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final MethodHelper methodHelper;
    @Override
    public UserDetails loadUserByUsername(String email) throws ResourceNotFoundException {
        User user = methodHelper.loadUserByEmail(email);
        return new UserDetailsImpl(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getPassword(),
                String.valueOf(user.getRoles().stream()
                    .map(role -> role.getRoleType().getName())
                    .collect(Collectors.toList()))
        );
    }
}
