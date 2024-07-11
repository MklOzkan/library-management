package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.payload.mapper.PublisherMapper;
import com.project.librarymanagement.payload.request.business.PublisherRequest;
import com.project.librarymanagement.payload.response.business.PublisherResponse;
import com.project.librarymanagement.repository.business.PublisherRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherMapper publisherMapper;

    private final MethodHelper methodHelper;

    private final PublisherRepository publisherRepository;

    public List<PublisherResponse> getAllPublishers() {
        return publisherRepository.findAll()
                .stream().map(publisherMapper :: mapPublisherToPublisherResponse).collect(Collectors.toList());
    }

    public PublisherResponse createPublisher(PublisherRequest publisherRequest) {
        Publisher publisher = publisherMapper.mapPublisherRequestToPublisher(publisherRequest);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return publisherMapper.mapPublisherToPublisherResponse(savedPublisher);
    }

    public PublisherResponse getById(Long id) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isPresent()) {
            Publisher publisher = publisherOptional.get();
            return publisherMapper.mapPublisherToPublisherResponse(publisher);
        }
        // not completed yet
        return null;
    }
}
