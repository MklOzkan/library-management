package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.payload.mapper.PublisherMapper;
import com.project.librarymanagement.payload.messages.SuccessMessages;
import com.project.librarymanagement.payload.request.business.PublisherRequest;
import com.project.librarymanagement.payload.response.business.PublisherResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.repository.business.PublisherRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.service.helper.PageableHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherMapper publisherMapper;

    private final MethodHelper methodHelper;

    private final PageableHelper pageableHelper;

    private final PublisherRepository publisherRepository;

    public Page<PublisherResponse> getAllPublishers(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return publisherRepository.findAll(pageable).map(publisherMapper::mapPublisherToPublisherResponse);
    }

    public ResponseMessage<PublisherResponse> createPublisher(PublisherRequest publisherRequest, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getAttribute("email").toString();
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        //check if user is an Admin
        methodHelper.isRoleAdmin(authenticatedUser);
        methodHelper.isPublisherExistByName(publisherRequest.getName());
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

    public ResponseMessage<PublisherResponse> updatePublisher(Long id, PublisherRequest publisherRequest, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getAttribute("email").toString();
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        //check if user is an Admin
        methodHelper.isRoleAdmin(authenticatedUser);
        Publisher publisher = methodHelper.getPublisherById(id);
        publisher.setName(publisherRequest.getName());
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_UPDATE)
                .returnBody(publisherMapper.mapPublisherToPublisherResponse(updatedPublisher))
                .build();
    }

    public ResponseMessage<PublisherResponse> deletePublisher(Long id, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getAttribute("email").toString();
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        //check if user is an Admin
        methodHelper.isRoleAdmin(authenticatedUser);
        Publisher publisher = methodHelper.getPublisherById(id);
        publisherRepository.deleteById(id);
        return ResponseMessage.<PublisherResponse>builder()
                    .message(SuccessMessages.PUBLISHER_DELETE)
                    .returnBody(publisherMapper.mapPublisherToPublisherResponse(publisher))
                    .build();
    }
}
