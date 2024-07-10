package com.project.librarymanagement.controller.business;

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
    public List<PublisherResponse> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

}
