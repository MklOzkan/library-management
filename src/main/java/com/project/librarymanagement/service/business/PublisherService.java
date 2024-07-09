package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.repository.business.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }
}
