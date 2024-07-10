package com.project.librarymanagement.payload.mapper;

import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.payload.request.business.PublisherRequest;
import com.project.librarymanagement.payload.response.business.PublisherResponse;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public Publisher mapPublisherRequestToPublisher(PublisherRequest publisherRequest) {
        Publisher publisher = Publisher
                .builder().name(publisherRequest.getName()).build();

        return publisher;
    }

    public PublisherResponse mapPublisherToPublisherResponse(Publisher publisher) {
        return PublisherResponse
                .builder().id(publisher.getId()).name(publisher.getName()).build();
    }

}
