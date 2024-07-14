package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.business.Loan;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.BadRequestException;
import com.project.librarymanagement.payload.mapper.LoanMapper;
import com.project.librarymanagement.payload.messages.ErrorMessages;
import com.project.librarymanagement.payload.request.business.LoanRequest;
import com.project.librarymanagement.payload.response.business.LoanResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.repository.business.BookRepository;
import com.project.librarymanagement.repository.business.LoanRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final MethodHelper methodHelper;
    private final LoanMapper loanMapper;
    private final BookRepository bookRepository;

    public ResponseMessage<LoanResponse> createLoan(LoanRequest loanRequest) {
        User user = methodHelper.findUserById(loanRequest.getUser().getId());

        checkIfUserHasAlreadyBorrowedBook(user.getId());
        checkUserScore(user.getId(), loanRequest.getBookIds());
        checkIfBookIsAvailable(loanRequest.getBookIds());
        List<Book> books = null;//methodHelper.findBooksByIds(loanRequest.getBookIds());
        Loan loan = loanMapper.mapLoanRequestToLoan(loanRequest, books, user);
        for (Book book: books){
            book.setLoanable(false);
            bookRepository.save(book);
        }
        loan.setActive(true);

        Loan savedLoan = loanRepository.save(loan);

        return ResponseMessage.<LoanResponse>builder()
                .message("Loan created successfully")
                .returnBody(loanMapper.mapLoanToLoanResponseForAdminAndEmployee(savedLoan))
                .build();

    }

    private void checkUserScore(Long userId, List<Long> bookIds) {
        User user = methodHelper.findUserById(userId);
        List<Book> books = new ArrayList<>();
        for (Long id: bookIds){
            books.add(methodHelper.isBookExist(id));
        }
        if(user.getScore()<=-2) {
            if (books.size() > 1) {
                throw new BadRequestException(String.format(ErrorMessages.CANNOT_HAVE_MORE_BOOKS, userId, 1));
            }
        } else if (user.getScore()==-1) {
            if(books.size()>2){
                throw new BadRequestException(String.format(ErrorMessages.CANNOT_HAVE_MORE_BOOKS, userId, 2));
            }
        } else if (user.getScore()==0) {
            if(books.size()>3){
                throw new BadRequestException(String.format(ErrorMessages.CANNOT_HAVE_MORE_BOOKS, userId, 3));
            }
        } else if (user.getScore()==1) {
            if (books.size()>4){
                throw new BadRequestException(String.format(ErrorMessages.CANNOT_HAVE_MORE_BOOKS, userId, 4));
            }
        }else {
            if (books.size()>5){
                throw new BadRequestException(String.format(ErrorMessages.CANNOT_HAVE_MORE_BOOKS, userId, 5));
            }
        }
    }

    private void checkIfUserHasAlreadyBorrowedBook(Long userId) {
        User user = methodHelper.findUserById(userId);
        List<Loan> loans = user.getLoan();
        for (Loan loan: loans){
            if(loan.getActive()){
                int size = loan.getBooks().size();
                throw new BadRequestException("You have already borrowed " + size + " books");
            }
        }
    }

    private void checkIfBookIsAvailable(List<Long> bookIds) {
        for (Long bookId: bookIds) {
            Book book = null;//methodHelper.findBookById(bookId);
            if (book.getBuiltIn()) {
                throw new BadRequestException("Book is not available");
            }
        }
    }
}
