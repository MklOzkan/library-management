package com.project.librarymanagement.service.business;

import com.project.librarymanagement.payload.response.business.ReportResponse;
import org.springframework.stereotype.Service;

@Service
public class ReportService {


//    It will return following object
//• Number of books
//• Numeber of authors
//• Number of publishers
//• Number of categories
//• Number of loans
//• Number of books which not returned
//• Number of expired books
//• Number of members

    //will be found total count of them and given as parametter


    public ReportResponse getALLReport(int books,
                                       int authors, int publishers, int categories,
                                       int loans, int unReturnedBooks,
                                       int expiredBooks,
                                       int members) {
        return new ReportResponse().builder()
                .books(books)
                .authors(authors)
                .publishers(publishers)
                .categories(categories)
                .loans(loans)
                .unReturnedBooks(unReturnedBooks)
                .expiredBooks(expiredBooks)
                .members(members)
                .build();
    }
}
