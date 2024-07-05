package com.project.librarymanagement.payload.request.user;

import com.project.librarymanagement.payload.request.abstracts.BaseUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UserRequest extends BaseUserRequest {
}
