package com.project.librarymanagement.controller.business;

import com.project.librarymanagement.payload.request.business.PublisherRequest;
import com.project.librarymanagement.payload.response.business.PublisherResponse;
import com.project.librarymanagement.service.business.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    public List<PublisherResponse> getAll() {
        return publisherService.getAllPublishers();
    }

    @GetMapping ("/{id}")
    public PublisherResponse getById(@PathVariable Long id) {
        return publisherService.getById(id);
    }

    @PostMapping
    public PublisherResponse createPublisher(@RequestBody PublisherRequest publisherRequest) {
        return publisherService.createPublisher(publisherRequest);
    }

}
