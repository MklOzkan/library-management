package com.project.librarymanagement.payload.request.business;

import com.project.librarymanagement.entity.enums.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {


    private Long id;
    private Categories categoryName;

    private Boolean builtIn;

    private int sequence;
}
