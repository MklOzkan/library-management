package com.project.librarymanagement.payload.response.business;

import com.project.librarymanagement.entity.business.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResponseWithoutUser {

        private Long loanId;
        private Long userId;
        private List<Book> books;
        private LocalDateTime loanDate;
        private LocalDateTime expireDate;
        private Boolean active;
        private String notes;

}
