package com.project.librarymanagement.repository.business;

import com.project.librarymanagement.entity.business.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
