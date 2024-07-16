package com.project.librarymanagement.controller.business;


import com.project.librarymanagement.payload.response.business.ReportResponse;
import com.project.librarymanagement.service.business.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLEngineResult;

@RestController
@PreAuthorize("hasAnyAuthority('Admin', 'Employee')")
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    @Autowired
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<ReportResponse> getReport() {
        var ReportList= reportService.getALLReport();
        return new ResponseEntity<>(ReportList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('Employee', 'Admin')")
    @GetMapping("/most-popular-books")
    public Page<ReportResponse> findMostPopularBooksByPage(
                @RequestParam (value = "page", defaultValue = "0") int page,
                @RequestParam(value = "size", defaultValue = "20") int size
                                    ) {


        return  reportService.findMostPopularBooksByPage(page,size);;
    }

}
