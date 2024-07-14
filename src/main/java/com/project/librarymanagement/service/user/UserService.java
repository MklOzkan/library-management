package com.project.librarymanagement.service.user;

import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.payload.mapper.UserMapper;
import com.project.librarymanagement.payload.request.user.UserRequest;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.payload.response.user.UserResponse;
import com.project.librarymanagement.repository.user.UserRepository;
import com.project.librarymanagement.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String role) {

        uniquePropertyValidator.checkDuplicate(
                userRequest.getPhoneNumber(),
                userRequest.getEmail());
        //map DTO -> entity
        User user = userMapper.mapUserRequestToUser(userRequest, role);
        //save operation
        User savedUser = userRepository.save(user);
        //map entity to DTO and return it
        return ResponseMessage.<UserResponse>builder()
                .message("User created successfully")
                .returnBody(userMapper.mapUserToUserResponse(savedUser))
                .build();

    }

}
