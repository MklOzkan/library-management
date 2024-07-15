package com.project.librarymanagement.payload.request.business;

import com.project.librarymanagement.entity.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequest {
    @NotNull(message = "User id must not be empty")
    private User user;
    @NotNull(message = "Book id must not be empty")
    private List<Long> bookIds;
    private String notes;
}
