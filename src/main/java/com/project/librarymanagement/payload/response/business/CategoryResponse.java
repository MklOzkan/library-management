package com.project.librarymanagement.payload.response.business;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.librarymanagement.entity.enums.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {
    private Long id;
    private Categories categoryName;

    private Boolean builtIn;

    private int sequence;
}
