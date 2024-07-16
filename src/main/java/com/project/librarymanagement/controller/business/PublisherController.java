package com.project.librarymanagement.controller.business;

import com.project.librarymanagement.payload.request.business.PublisherRequest;
import com.project.librarymanagement.payload.response.business.PublisherResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.service.business.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    //TODO: Pageable
    @GetMapping
    public Page<PublisherResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type) {
        return publisherService.getAllPublishers(page, size, sort, type);
    }

    @GetMapping ("/{id}")
    public ResponseMessage<PublisherResponse> getById(@PathVariable Long id) {
        return publisherService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping
    public ResponseMessage<PublisherResponse> createPublisher(@RequestBody @Valid PublisherRequest publisherRequest) {
        return publisherService.createPublisher(publisherRequest);
    }

    @PutMapping ("/{id}")
    public ResponseMessage<PublisherResponse> updatePublisher(@PathVariable Long id, @RequestBody @Valid PublisherRequest publisherRequest) {
        return publisherService.updatePublisher(id, publisherRequest);
    }

    @DeleteMapping ("/{id}")
    public ResponseMessage<PublisherResponse> deletePublisherById(@PathVariable @Valid Long id) {
        return publisherService.deletePublisher(id);
    }

}
