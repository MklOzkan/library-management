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
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.payload.response.user.UserResponse;
import com.project.librarymanagement.repository.user.UserRepository;
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

    public Page<UserResponse> getAllUsersByPage(int page, int size, String sort, String type, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        methodHelper.isRoleAdminOrEmployee(authenticatedUser);
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return userRepository.findAll(pageable).map(userMapper::mapUserToUserResponse);
    }

    public UserResponse findUserById(Long id, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        methodHelper.isRoleAdminOrEmployee(authenticatedUser);
        User user = fetchUserById(id);
        return userMapper.mapUserToUserResponse(user);
    }

    public ResponseMessage<String> deleteUserById(Long id, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        methodHelper.isRoleAdminOrEmployee(authenticatedUser);
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

    public UserResponse updateUserById(UserRequestWithoutPassword userRequest, Long id, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        methodHelper.isRoleAdminOrEmployee(authenticatedUser);
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

    public ResponseMessage<UserResponse> addRoleToUser(Long userId, String roleName, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");//get email from token
        //load user by email
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        //check if user is Admin or Employee
        methodHelper.isRoleAdminOrEmployee(authenticatedUser);
        //find user by given id
        User user = methodHelper.findUserById(userId);
        //fetch roles of user
        List<Role> roles = user.getRoles().stream().toList();
        //check if user has more than 2 roles
        checkRoleCount(roles, user.getId());
        //get the role of user
        Role userRole = roles.get(0);
        //find the role by given roleName
        Role role = methodHelper.findRoleByName(roleName);
        //check if the role is already exist
        if(roles.contains(role)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_EXIST_ROLE_MESSAGE, role.getRoleName()));
        }
        //check if the user is Admin and the role is Employee
        if (userRole.getRoleName().equals("Admin")&&role.getRoleName().equals("Employee")) {
            throw new ConflictException(ErrorMessages.CANNOT_ADD_EMPLOYEE_ROLE_TO_ADMIN_MESSAGE);
        }
        //check if the user is Employee and the role is Admin
        if (userRole.getRoleName().equals("Employee")&&role.getRoleName().equals("Admin")) {
            throw new ConflictException(ErrorMessages.CANNOT_ADD_ADMIN_ROLE_TO_EMPLOYEE_MESSAGE);
        }
        //add the role to user
        user.getRoles().add(role);
        //save the user
        userRepository.save(user);
        //return the response
        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.ROLE_SAVE)
                .returnBody(userMapper.mapUserToUserResponse(user))
                .build();
    }

    private void checkRoleCount(List<Role> roles, Long userId) {
        if (roles.size() > 1) {
            throw new ConflictException(String.format(ErrorMessages.CANNOT_HAVE_MORE_THAN_TWO_ROLE_MESSAGE, userId));
        }
    }

    public ResponseMessage<UserResponse> saveUserByAdmin(UserRequest userRequest, String userRole, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        //checks the activeRole, if it is not Admin then it throws an exception
        methodHelper.isRoleAdmin(authenticatedUser);
        //checks the role, if it is Member than it throws an exception
        methodHelper.checkIfRoleMember(userRole);
        //checks the user is email and phone number unique
        uniquePropertyValidator.checkDuplicate(userRequest.getPhoneNumber(), userRequest.getEmail());
        //maps the DTO to entity
        User user = userMapper.mapUserRequestToUser(userRequest, userRole);
        //saves the user
        User savedUser = userRepository.save(user);
        //maps the entity to DTO and returns it
        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_SAVE)
                .returnBody(userMapper.mapUserToUserResponse(savedUser))
                .build();
    }
}
