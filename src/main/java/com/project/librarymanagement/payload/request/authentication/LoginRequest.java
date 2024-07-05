package com.project.librarymanagement.payload.request.authentication;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
  
  @NotNull(message = "Username must not be empty")
  public String username;
  
  @NotNull(message = "Password must not be empty")
  public String password;

}
