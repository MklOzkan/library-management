package com.project.librarymanagement.service.user;

import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.payload.mapper.UserMapper;
import com.project.librarymanagement.payload.messages.ErrorMessages;
import com.project.librarymanagement.payload.request.user.UserRequest;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.payload.response.user.UserResponse;
import com.project.librarymanagement.repository.user.UserRepository;
import com.project.librarymanagement.service.helper.PageableHelper;
import com.project.librarymanagement.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final PageableHelper pageableHelper;


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

    //Same as saveUser but without the role
    public UserResponse createUser(UserRequest userRequest) {
        //Validate user is unique
        uniquePropertyValidator.checkDuplicate(userRequest.getPhoneNumber(), userRequest.getEmail());
        User user = userMapper.mapUserRequestToUser(userRequest, "Member");
        User savedUser = userRepository.save(user);
        return userMapper.mapUserToUserResponse(savedUser);
    }

    public Page<UserResponse> getAllUsersByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return userRepository.findAll(pageable).map(userMapper::mapUserToUserResponse);
    }

    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id))
        );
        return userMapper.mapUserToUserResponse(user);
    }
}
