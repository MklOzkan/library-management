package com.project.librarymanagement.payload.mapper;

import com.project.librarymanagement.entity.enums.RoleType;
import com.project.librarymanagement.entity.user.Role;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.payload.request.abstracts.BaseUserRequest;
import com.project.librarymanagement.payload.request.user.UserRequestWithoutPassword;
import com.project.librarymanagement.payload.response.user.UserResponse;
import com.project.librarymanagement.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public User mapUserRequestToUser(BaseUserRequest userRequest, String role) {
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .birthDate(userRequest.getBirthDate())
                .address(userRequest.getAddress())
                .phoneNumber(userRequest.getPhoneNumber())
                .gender(userRequest.getGender())
                .email(userRequest.getEmail())
                .builtIn(userRequest.getBuildIn())
                .build();
        //role part will be set here
        if (role.equalsIgnoreCase(RoleType.ADMIN.getName())) {
            //if username is Admin then we set this user buildIn -> true
            if (Objects.equals(userRequest.getEmail(), "Admin")) {
                user.setBuiltIn(true);
            }
            user.setRoles(roleService.getRole(RoleType.ADMIN));
        } else if (role.equalsIgnoreCase("Employee")) {
            user.setRoles(roleService.getRole(RoleType.EMPLOYEE));
        } else if (role.equalsIgnoreCase("Member")) {
            user.setRoles(roleService.getRole(RoleType.MEMBER));
        }
        else {
            throw new ResourceNotFoundException("Role not found with name: " + role);
        }
        return user;
    }

    // Entity -> DTO

    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastLame(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .birthDay(user.getBirthDate())
                .address(user.getAddress())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                .build();
    }


    //UserRequestWithoutPassword -> Entity
    public User mapUserRequestWithoutPasswordToUser(UserRequestWithoutPassword userRequestWithoutPassword){
        return User.builder()
                .firstName(userRequestWithoutPassword.getFirstName())
                .lastName(userRequestWithoutPassword.getLastName())
                .gender(userRequestWithoutPassword.getGender())
                .score(userRequestWithoutPassword.getScore())
                .address(userRequestWithoutPassword.getAddress())
                .phoneNumber(userRequestWithoutPassword.getPhoneNumber())
                .birthDate(userRequestWithoutPassword.getBirthDate())
                .email(userRequestWithoutPassword.getEmail())
                .build();
    }
}
