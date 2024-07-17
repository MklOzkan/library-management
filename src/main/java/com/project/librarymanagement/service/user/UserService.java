package com.project.librarymanagement.service.user;

import com.project.librarymanagement.entity.user.Role;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.ConflictException;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.payload.mapper.UserMapper;
import com.project.librarymanagement.payload.messages.ErrorMessages;
import com.project.librarymanagement.payload.messages.SuccessMessages;
import com.project.librarymanagement.payload.request.authentication.LoginRequest;
import com.project.librarymanagement.payload.request.user.UserRequest;
import com.project.librarymanagement.payload.request.user.UserRequestWithoutPassword;
import com.project.librarymanagement.payload.response.business.AuthenticatedUserLoanResponse;
import com.project.librarymanagement.payload.response.business.LoanResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.payload.response.user.UserResponse;
import com.project.librarymanagement.repository.user.UserRepository;
import com.project.librarymanagement.service.business.LoanService;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.service.helper.PageableHelper;
import com.project.librarymanagement.validator.UniquePropertyValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;
    private final AuthenticationService authenticationService;
    private final LoanService loanService;


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
        User user = fetchUserById(id);
        return userMapper.mapUserToUserResponse(user);
    }

    public ResponseMessage<String> deleteUserById(Long id) {
        User user = fetchUserById(id);
        userRepository.deleteById(id); //using this for better performance
        return ResponseMessage.<String>builder()
                .message(String.format("User with id: %s deleted successfully", id))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private User fetchUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id))
        );
    }

    public UserResponse updateUserById(UserRequestWithoutPassword userRequest, Long id) {
        User user = fetchUserById(id);
        methodHelper.checkBuildIn(user);
        uniquePropertyValidator.checkUniqueProperties(user, userRequest);
        User updatedUser = userMapper.mapUserRequestWithoutPasswordToUser(userRequest);
        updatedUser.setId(user.getId());
        updatedUser.setRoles(user.getRoles());
        updatedUser.setActiveRole(user.getActiveRole());
        User savedUser = userRepository.save(updatedUser);
        return userMapper.mapUserToUserResponse(savedUser);
    }

    public UserResponse returnAuthenticatedUser(HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getUserPrincipal().getName();
        User user = methodHelper.loadUserByEmail(email);
        LoginRequest loginRequest = LoginRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .roleToLogin(user.getActiveRole())
                .build();

        authenticationService.authenticateUser(loginRequest);
        return userMapper.mapUserToUserResponse(user);
    }

    public ResponseMessage<UserResponse> addRoleToUser(Long userId, String roleName) {
        User user = methodHelper.findUserById(userId);
        List<Role> roles = user.getRoles().stream().toList();
        checkRoleCount(roles);
        Role userRole = roles.get(0);
        Role role = methodHelper.findRoleByName(roleName);

        if(roles.contains(role)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_EXIST_ROLE_MESSAGE, role.getRoleName()));
        }

        if (userRole.getRoleName().equals("Admin")&&role.getRoleName().equals("Employee")||
                userRole.getRoleName().equals("Employee")&&role.getRoleName().equals("Admin")) {
            throw new ConflictException(ErrorMessages.MANAGER_CANNOT_HAVE_ANOTHER_MANAGER_ROLE_MESSAGE);
        }

        user.getRoles().add(role);
        userRepository.save(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.ROLE_SAVE)
                .returnBody(userMapper.mapUserToUserResponse(user))
                .build();
    }

    private void checkRoleCount(List<Role> roles) {
        if (roles.size() > 2) {
            throw new ConflictException(ErrorMessages.CANNOT_HAVE_MORE_THAN_TWO_ROLE_MESSAGE);
        }
    }

    public AuthenticatedUserLoanResponse returnAuthenticatedUserLoans(HttpServletRequest httpServletRequest, int page, int size, String sort, String type) {
        UserResponse userResponse = returnAuthenticatedUser(httpServletRequest);
        Page<LoanResponse> responsePage = loanService.getAllLoansByPage(page, size, sort, type, httpServletRequest);
        return AuthenticatedUserLoanResponse.builder()
                .loanResponsePage(responsePage)
                .userResponse(userResponse)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
