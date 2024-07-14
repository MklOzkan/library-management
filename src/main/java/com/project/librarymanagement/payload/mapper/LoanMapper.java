package com.project.librarymanagement.payload.mapper;

import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.business.Loan;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.payload.request.business.LoanRequest;
import com.project.librarymanagement.payload.response.business.LoanResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class LoanMapper {

    public Loan mapLoanRequestToLoan(LoanRequest loanRequest, List<Book> books, User user){
        return Loan.builder()
                .books(books)
                .user(user)
                .notes(loanRequest.getNotes())
                .build();
    }

    public LoanResponse mapLoanToLoanResponseForAdminAndEmployee(Loan loan) {
        return LoanResponse.builder()
                .loanId(loan.getId())
                .loanDate(loan.getLoanDate())
                .expireDate(loan.getExpireDate())
                .userId(loan.getUser().getId())
                .books(loan.getBooks())
                .active(loan.getActive())
                .notes(loan.getNotes())
                .build();
    }

    public LoanResponse mapLoanToLoanResponseForMember(Loan loan) {
        return LoanResponse.builder()
                .loanId(loan.getId())
                .books(loan.getBooks())
                .userId(loan.getUser().getId())
                .loanDate(loan.getLoanDate())
                .expireDate(loan.getExpireDate())
                .active(loan.getActive())
                .build();
    }
}
