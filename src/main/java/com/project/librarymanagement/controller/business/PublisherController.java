package com.project.librarymanagement.controller.business;

import com.project.librarymanagement.entity.business.Publisher;
import com.project.librarymanagement.payload.request.business.PublisherRequest;
import com.project.librarymanagement.payload.response.business.PublisherResponse;
import com.project.librarymanagement.service.business.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    public PublisherResponse getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        return new PublisherResponse(publishers);
    }

}
