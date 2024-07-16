package com.project.librarymanagement.service.business;

import com.project.librarymanagement.payload.mapper.BookMapper;
import com.project.librarymanagement.payload.response.business.BookResponse;
import com.project.librarymanagement.payload.response.business.ReportResponse;
import com.project.librarymanagement.repository.business.BookRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.service.helper.PageableHelper;
import com.project.librarymanagement.service.helper.ReportHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {


        private final ReportHelper reportHelper;
        private final PageableHelper pageableHelper;
        private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    public ReportResponse getALLReport() {
        return reportHelper.getReport();
    }

    public Page<BookResponse> findMostPopularBooksByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties( page, size, sort, type);
        return bookRepository.findAll(pageable)
                .map(bookMapper::mapBookToBookResponse);
    }

    public Page<BookResponse> findUnreturnedBooksByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties( page, size, sort, type);
        return bookRepository.getUnreturnedBooks(pageable).map(bookMapper::mapBookToBookResponse);
    }

    public Page<BookResponse> findExpiredBooksByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties( page, size, sort, type);
        return bookRepository.getExpiredBooks(pageable).map(bookMapper::mapBookToBookResponse);
    }


    public Page<BookResponse> findMostBarrowedBooksByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties( page, size, sort, type);
        return bookRepository.getMostBarowedBooks(pageable).map(bookMapper::mapBookToBookResponse);
    }
}
