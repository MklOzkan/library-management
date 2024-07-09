package com.project.librarymanagement.repository.business;

import com.project.librarymanagement.entity.business.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {



}
