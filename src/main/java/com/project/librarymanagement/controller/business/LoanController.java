package com.project.librarymanagement.controller.business;

import com.project.librarymanagement.payload.request.business.LoanRequest;
import com.project.librarymanagement.payload.request.business.LoanUpdateRequest;
import com.project.librarymanagement.payload.response.business.LoanResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.service.business.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAnyAuthority('Member')")
    @GetMapping
    public Page<LoanResponse> getAllLoans(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "loanDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type){
        return loanService.getAllLoansByPage(page, size,sort,type,httpServletRequest);
    }

    @PreAuthorize("hasAnyAuthority('Member')")
    @GetMapping("/{loanId}")
    public ResponseMessage<LoanResponse> getLoanById(@PathVariable Long loanId, HttpServletRequest httpServletRequest){
        return loanService.getLoanById(loanId, httpServletRequest);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Employee')")
    @PutMapping("/{id}")
    public ResponseMessage<LoanResponse> updateLoan(@RequestBody @Valid LoanUpdateRequest loanUpdateRequest, @PathVariable Long id){
        return loanService.updateLoan(loanUpdateRequest, id);
    }
}
