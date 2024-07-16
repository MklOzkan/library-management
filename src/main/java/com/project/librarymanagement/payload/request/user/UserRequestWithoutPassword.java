package com.project.librarymanagement.payload.request.user;

import com.project.librarymanagement.payload.request.abstracts.AbstractUserRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class UserRequestWithoutPassword extends AbstractUserRequest {
}
