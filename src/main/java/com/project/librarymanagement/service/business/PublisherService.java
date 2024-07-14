package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.payload.mapper.PublisherMapper;
import com.project.librarymanagement.payload.messages.SuccessMessages;
import com.project.librarymanagement.payload.request.business.PublisherRequest;
import com.project.librarymanagement.payload.response.business.PublisherResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
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

    public ResponseMessage<PublisherResponse> createPublisher(PublisherRequest publisherRequest) {
        //TODO: validate if publisher already exists
        Publisher publisher = publisherMapper.mapPublisherRequestToPublisher(publisherRequest);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_SAVE)
                .returnBody(publisherMapper.mapPublisherToPublisherResponse(savedPublisher))
                .build();
    }

    public ResponseMessage<PublisherResponse> getById(Long id) {
        Publisher publisher = methodHelper.getPublisherById(id);
        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_FOUND)
                .returnBody(publisherMapper.mapPublisherToPublisherResponse(publisher))
                .build();
    }

    public ResponseMessage<PublisherResponse> updatePublisher(Long id, PublisherRequest publisherRequest) {
        Publisher publisher = methodHelper.getPublisherById(id);
        publisher.setName(publisherRequest.getName());
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_UPDATE)
                .returnBody(publisherMapper.mapPublisherToPublisherResponse(updatedPublisher))
                .build();
    }

    public ResponseMessage<Object> deletePublisher(Long id) {
        Object publisher = methodHelper.isPublisherExist(id);
        publisherRepository.deleteById(id);
        return ResponseMessage.<Object>builder()
                    .message(SuccessMessages.PUBLISHER_DELETE)
                    .returnBody(publisher)
                    .build();
    }
}
