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
        Publisher publisher = methodHelper.getPublisherById(id);
        return publisherMapper.mapPublisherToPublisherResponse(publisher);
    }

    public PublisherResponse updatePublisher(Long id, PublisherRequest publisherRequest) {
        Publisher publisher = methodHelper.getPublisherById(id);
        publisher.setName(publisherRequest.getName());
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return publisherMapper.mapPublisherToPublisherResponse(updatedPublisher);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
