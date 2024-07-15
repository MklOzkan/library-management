package com.project.librarymanagement.repository.business;

import com.project.librarymanagement.entity.business.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findByUserId(Long id, Pageable pageable);
    Optional<Loan> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
