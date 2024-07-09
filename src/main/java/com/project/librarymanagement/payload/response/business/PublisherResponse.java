package com.project.librarymanagement.payload.response.business;

import com.project.librarymanagement.entity.business.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublisherResponse {

    private List<Publisher> publishers;



}
