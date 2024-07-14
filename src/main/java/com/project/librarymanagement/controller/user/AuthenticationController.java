package com.project.librarymanagement.controller.user;

import com.project.librarymanagement.payload.request.authentication.LoginRequest;
import com.project.librarymanagement.payload.request.authentication.UpdatePasswordRequest;
import com.project.librarymanagement.payload.response.authentication.AuthResponse;
import com.project.librarymanagement.payload.response.user.UserResponse;
import com.project.librarymanagement.service.user.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse>authenticateUser(
          @RequestBody @Valid LoginRequest request) {
    return ResponseEntity.ok(authenticationService.authenticateUser(request));
  }

  /**
   * Any logged-in user can update her/his password
   */
  @PreAuthorize("hasAnyAuthority('Admin','Employee','Member')")
  @PatchMapping("/updatePassword")
  public ResponseEntity<String>updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest, HttpServletRequest request){
    authenticationService.updatePassword(updatePasswordRequest,request);
    return ResponseEntity.ok("Password Successfully Changed");
  }

  @PreAuthorize("hasAnyAuthority('Admin','Employee','Member')")
  @GetMapping("/user")
  public ResponseEntity<UserResponse> findByEmail(HttpServletRequest httpServletRequest){
    return ResponseEntity.ok(authenticationService.findByEmail(httpServletRequest));
  }
}
