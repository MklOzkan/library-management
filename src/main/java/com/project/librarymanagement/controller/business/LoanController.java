package com.project.librarymanagement.controller.business;

import com.project.librarymanagement.payload.request.business.LoanRequest;
import com.project.librarymanagement.payload.response.business.LoanResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.service.business.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PreAuthorize("hasAnyAuthority('Admin','Employee')")
    @PostMapping("/save")
    public ResponseMessage<LoanResponse> createLoan(@RequestBody @Valid LoanRequest loanRequest){
        return loanService.createLoan(loanRequest);
    }
}
