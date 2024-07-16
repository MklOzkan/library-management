package com.project.librarymanagement.controller.business;


import com.project.librarymanagement.payload.response.business.BookResponse;
import com.project.librarymanagement.payload.response.business.ReportResponse;
import com.project.librarymanagement.payload.response.user.UserResponse;
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

    //@PreAuthorize("hasAnyAuthority('Employee', 'Admin')")
    @GetMapping("/most-popular-books")
    public Page<BookResponse> findMostPopularBooksByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
                                    ) {
        return  reportService.findMostPopularBooksByPage(page, size, sort, type);
    }

    //@PreAuthorize("hasAnyAuthority('Employee', 'Admin')")
    @GetMapping("/unreturned-books")
    public Page<BookResponse> findUnreturnedBooksByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        return  reportService.findUnreturnedBooksByPage(page, size, sort, type);
    }

    //@PreAuthorize("hasAnyAuthority('Employee', 'Admin')")
    @GetMapping("/expired-books")
    public Page<BookResponse> findExpiredBooksByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        return  reportService.findExpiredBooksByPage(page, size, sort, type);
    }

    @PreAuthorize("hasAnyAuthority('Employee', 'Admin')")
    @GetMapping("/most-borrowers")
    public Page<UserResponse> findMostBarrowedBooksByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        return  reportService.findMostBarrowerByPage(page, size, sort, type);
    }

}
