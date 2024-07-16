package com.project.librarymanagement.service.helper;

import com.project.librarymanagement.payload.response.business.ReportResponse;
import com.project.librarymanagement.repository.business.*;
import com.project.librarymanagement.repository.user.RoleRepository;
import com.project.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ReportHelper {


    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    private final CategoryRepository categoryRepository;
    private final LoanRepository loanRepository;

    private final ReportResponse reportResponse;


    public ReportResponse getReport()
    {
        reportResponse .builder()
                .books(getBookCount())
                .authors(getAuthorsCount())
                .publishers(getPublishersCount())
                .categories(getCategoriesCount())
                .loans(getLoansCount())
                .unReturnedBooks(getUnreturnedBookCounts())
                .expiredBooks(getExpiredBooksCount())
                .members(getMemebersCount())
                .build();

        return  reportResponse;
    }

    //find all book count
    public int getBookCount ()
    {
        return (int) bookRepository.count();
    }

    public int getAuthorsCount ()
    {
        return (int) authorRepository.count();
    }

    public int getPublishersCount ()
    {
        return (int) publisherRepository.count();
    }

    public int getCategoriesCount ()
    {
        return (int) categoryRepository.count();
    }

    public int getLoansCount ()
    {
        return (int) loanRepository.count();
    }

    public int getUnreturnedBookCounts()
    {
        return  bookRepository.getUnrentedBookCount().intValue();
    }



    public int getExpiredBooksCount ()
    {
        return loanRepository.getDateExpiredBookCount().intValue();
    }

    public int getMemebersCount ()
    {
        return (int) userRepository.count();
    }

}
