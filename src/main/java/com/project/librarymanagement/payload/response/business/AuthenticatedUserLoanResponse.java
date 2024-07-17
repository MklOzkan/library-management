package com.project.librarymanagement.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.librarymanagement.payload.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticatedUserLoanResponse {

    Page<LoanResponse> loanResponsePage;
    UserResponse userResponse;
    HttpStatus httpStatus;
}
