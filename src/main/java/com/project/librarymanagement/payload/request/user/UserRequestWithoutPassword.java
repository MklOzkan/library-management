package com.project.librarymanagement.payload.request.user;

import com.project.librarymanagement.payload.request.abstracts.AbstractUserRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserRequestWithoutPassword extends AbstractUserRequest {
}
