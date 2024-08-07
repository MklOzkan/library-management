package com.project.librarymanagement.repository.business;

import com.project.librarymanagement.entity.business.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query( "SELECT COUNT(*) FROM Book WHERE loanable = FALSE")
    Long getUnrentedBookCount();


    @Query("SELECT b FROM Book b WHERE b.loanable = FALSE")
    Page<Book> getUnreturnedBooks(Pageable page);


    @Query("SELECT b FROM Book b JOIN b.loans l WHERE l.returnDate < CURRENT_TIMESTAMP")
    Page<Book> getExpiredBooks(Pageable page);


    //It will be change for most barrowed count
    @Query("SELECT b FROM Book b ORDER BY COUNT(b.rentalAmount) DESC")
   Page<Book> getMostBarowedBooks(Pageable pageable);
}
