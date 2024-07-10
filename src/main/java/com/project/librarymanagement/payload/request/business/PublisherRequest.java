package com.project.librarymanagement.payload.request.business;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublisherRequest {

//    private int page = 0;
//    private int size = 20;
//    private String sort = "name";
//    private String type = "asc";

    private String name;

}
