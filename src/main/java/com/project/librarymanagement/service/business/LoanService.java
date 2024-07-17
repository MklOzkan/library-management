package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Book;
import com.project.librarymanagement.entity.business.Loan;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.BadRequestException;
import com.project.librarymanagement.payload.mapper.LoanMapper;
import com.project.librarymanagement.payload.messages.ErrorMessages;
import com.project.librarymanagement.payload.messages.SuccessMessages;
import com.project.librarymanagement.payload.request.business.LoanRequest;
import com.project.librarymanagement.payload.request.business.LoanUpdateRequest;
import com.project.librarymanagement.payload.response.business.LoanResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.repository.business.BookRepository;
import com.project.librarymanagement.repository.business.LoanRepository;
import com.project.librarymanagement.repository.user.UserRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.service.helper.PageableHelper;
import com.project.librarymanagement.service.user.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final PageableHelper pageableHelper;
    private final UserRepository userRepository;

    public ResponseMessage<LoanResponse> createLoan(LoanRequest loanRequest) {
        User user = methodHelper.findUserById(loanRequest.getUser().getId());//check if user exits then get user

        checkIfUserHasAlreadyBorrowedBook(user.getId());//check if user has already borrowed a book
        checkUserScore(user.getId(), loanRequest.getBookIds());//check user score
        checkIfBookIsAvailable(loanRequest.getBookIds());//check if book is available
        List<Book> books = methodHelper.findBooksByIds(loanRequest.getBookIds());//get books
        Loan loan = loanMapper.mapLoanRequestToLoan(loanRequest, books, user);//map loan request to loan

        loan.setActive(true);

        Loan savedLoan = loanRepository.save(loan);//save loan
        for (Book book: books){//set books loanable to false
            book.setLoanable(false);
            book.setLoan(loan);
            book.setRentalAmount(book.getRentalAmount()+1);
            bookRepository.save(book);
        }
        user.setBorrowedBookCount(user.getBorrowedBookCount()+books.size());//set borrowed book count
        user.setBorrowCount(user.getBorrowCount()+1);//set borrow count
        userRepository.save(user);

        return ResponseMessage.<LoanResponse>builder()
                .message(SuccessMessages.LOAN_SAVE)
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
            Book book = methodHelper.isBookExist(bookId);
            if (book.getBuiltIn()) {
                throw new BadRequestException("Book is not available");
            }
        }
    }

    public Page<LoanResponse> getAllLoansByPage(int page, int size, String sort, String type, HttpServletRequest httpServletRequest) {
        //Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.loadUserByEmail(email);

        Sort.Direction direction = type.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return loanRepository.findByUserId(user.getId(),pageable)
                .map(loanMapper::mapLoanToLoanResponseForMember);

    }

    public ResponseMessage<LoanResponse> getLoanById(Long loanId, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.loadUserByEmail(email);
        Loan loan = methodHelper.findLoanById(loanId);
        if(!loan.getUser().getId().equals(user.getId())){
            throw new BadRequestException("You are not authorized to see this loan");
        }
        return ResponseMessage.<LoanResponse>builder()
                .message("Loan found")
                .returnBody(loanMapper.mapLoanToLoanResponseForMember(loan))
                .build();
    }

    public ResponseMessage<LoanResponse> updateLoan(LoanUpdateRequest loanUpdateRequest, Long id) {
        Loan loan = methodHelper.findLoanById(id);
        User user = methodHelper.findUserById(loan.getUser().getId());
        LocalDateTime returnDate = LocalDateTime.now();
        if (checkReturnDate(returnDate, loan.getExpireDate())){
            loan.setReturnDate(returnDate);
            loan.setActive(false);
            user.setScore(user.getScore()+1);

            for (Book book: loan.getBooks()){
                book.setLoanable(true);
                bookRepository.save(book);
            }
            loanRepository.save(loan);
            userRepository.save(user);
            return ResponseMessage.<LoanResponse>builder()
                    .message(SuccessMessages.LOAN_UPDATE_BEFORE_EXPIRE_DATE)
                    .returnBody(loanMapper.mapLoanToLoanResponseForAdminAndEmployee(loan))
                    .build();
        } else {
            user.setScore(user.getScore()-1);
            loan.setActive(false);
            for (Book book: loan.getBooks()){
                book.setLoanable(true);
                bookRepository.save(book);
            }
            loanRepository.save(loan);
            return ResponseMessage.<LoanResponse>builder()
                    .message(SuccessMessages.LOAN_UPDATE_AFTER_EXPIRE_DATE)
                    .returnBody(loanMapper.mapLoanToLoanResponseForAdminAndEmployee(loan))
                    .build();
        }
    }

    private boolean checkReturnDate(LocalDateTime returnDate, LocalDateTime expireDate) {
        return returnDate.isBefore(expireDate);
    }

    public Page<LoanResponse> getLoansByUserId(Long userId, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        User user = methodHelper.findUserById(userId);
        return loanRepository.findByUserId(user.getId(), pageable)
                .map(loanMapper::mapLoanToLoanResponseForAdminAndEmployee);
    }
}
