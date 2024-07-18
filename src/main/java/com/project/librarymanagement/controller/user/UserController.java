package com.project.librarymanagement.controller.user;

import com.project.librarymanagement.payload.request.user.RoleRequest;
import com.project.librarymanagement.payload.request.user.UserRequest;
import com.project.librarymanagement.payload.request.user.UserRequestWithoutPassword;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.payload.response.user.UserResponse;
import com.project.librarymanagement.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //1. /register -> create user
    //Everyone has authority
    @PostMapping("/register")
    public ResponseEntity<UserResponse>register(@RequestBody @Valid UserRequest userRequest){
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    //1.1 create user with role
    @PostMapping("/save/{userRole}")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseMessage<UserResponse>createUser(@RequestBody @Valid UserRequest userRequest, @PathVariable String userRole, HttpServletRequest httpServletRequest){
        return userService.saveUserByAdmin(userRequest, userRole, httpServletRequest);
    }

    @PostMapping("/user")
    @PreAuthorize("hasAnyAuthority('Admin','Employee','Member')")
    public ResponseEntity<UserResponse>returnAuthenticatedUser(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(userService.returnAuthenticatedUser(httpServletRequest));
    }

    //2. /users -> will  return pageable user
    @GetMapping
    @PreAuthorize("hasAnyAuthority('Admin','Employee')")
    public ResponseEntity<Page<UserResponse>>getAllUsersByPage(
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size,
            @RequestParam (value = "sort", defaultValue = "firstName") String sort,
            @RequestParam (value = "type", defaultValue = "desc") String type,
            HttpServletRequest httpServletRequest
    ){
        return ResponseEntity.ok(userService.getAllUsersByPage(page, size, sort, type,httpServletRequest));
    }

    //3. Fetch user via id
    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Employee')")
    public ResponseEntity<UserResponse>getUserById(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(userService.findUserById(id, httpServletRequest));
    }

    //4. delete user via id
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Employee')")
    public ResponseMessage<String>deleteUserById(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return userService.deleteUserById(id, httpServletRequest);
    }

    //5. update user via id
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Employee')")
    public ResponseEntity<UserResponse>updateUserById(@RequestBody @Valid UserRequestWithoutPassword userRequest, @PathVariable Long id, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(userService.updateUserById(userRequest, id, httpServletRequest));
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/{id}")
    public ResponseMessage<UserResponse> AddRole(@PathVariable Long id, @RequestBody @Valid RoleRequest roleRequest, HttpServletRequest httpServletRequest) {
        return userService.addRoleToUser(id, roleRequest.getRoleName(), httpServletRequest);
    }
}
