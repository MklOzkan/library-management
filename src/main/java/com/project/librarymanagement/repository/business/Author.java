package com.project.librarymanagement.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Author extends JpaRepository<Author,Long> {
}
