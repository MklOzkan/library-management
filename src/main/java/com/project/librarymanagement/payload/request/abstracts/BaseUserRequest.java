package com.project.librarymanagement.payload.request.abstracts;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserRequest extends AbstractUserRequest{

    @NotNull(message = "Please enter your password")
    @Size(min = 8,max = 60,message = "Your password should be at least 8 chars and maximum 60 chars")
    private String password;

    private Boolean buildIn = false;
}
