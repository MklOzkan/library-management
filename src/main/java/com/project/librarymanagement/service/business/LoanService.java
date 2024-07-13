package com.project.librarymanagement.service.business;

import com.project.librarymanagement.repository.business.LoanRepository;
import com.project.librarymanagement.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final MemberService memberService;
}
