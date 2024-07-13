package com.project.librarymanagement.controller.user;

import com.project.librarymanagement.payload.request.user.UserRequest;
import com.project.librarymanagement.payload.response.user.UserResponse;
import com.project.librarymanagement.service.user.UserService;
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
    //TODO this end-point should be usable by anyone, added Admin for test but getting error
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<UserResponse>register(@RequestBody @Valid UserRequest userRequest){
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }


    //2. /users -> will  return pageable user
    @GetMapping("/{userRole}")
    @PreAuthorize("hasAnyAuthority('Admin','Employee')")
    public ResponseEntity<Page<UserResponse>>getAllUsersByPage(
            @PathVariable String userRole,
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size,
            @RequestParam (value = "sort", defaultValue = "name") String sort,
            @RequestParam (value = "type", defaultValue = "desc") String type
    ){
        return ResponseEntity.ok(userService.getAllUsersByPage(page, size, sort, type));
    }

    //3. Fetch user via id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Employee')")
    public ResponseEntity<UserResponse>getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }
}
