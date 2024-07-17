package com.project.librarymanagement.repository.business;

import com.project.librarymanagement.entity.business.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findByUserId(Long id, Pageable pageable);
    Optional<Loan> findByUserId(Long userId);

    boolean existsByUserId(Long userId);


    @Query(value = "SELECT COUNT(*) FROM loans WHERE loans.expire_date < NOW()", nativeQuery = true)
    Long getDateExpiredBookCount();

    @Query("SELECT l FROM Loan l JOIN l.books b WHERE b.id = :id")
    Page<Loan> findByBookId(@Param("id") Long id, Pageable pageable);
}
