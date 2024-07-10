package com.project.librarymanagement.payload.response.business;

import com.project.librarymanagement.entity.business.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublisherResponse {

    private Long id;

    private String name;

}
