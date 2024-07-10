package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.payload.mapper.PublisherMapper;
import com.project.librarymanagement.payload.response.business.PublisherResponse;
import com.project.librarymanagement.repository.business.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherMapper publisherMapper;

    private final PublisherRepository publisherRepository;

    public List<PublisherResponse> getAllPublishers() {
        return publisherRepository.findAll()
                .stream().map(publisherMapper :: mapPublisherToPublisherResponse).collect(Collectors.toList());
    }
}
