package com.project.librarymanagement.payload.mapper;

import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.payload.request.business.CategoryRequest;
import com.project.librarymanagement.payload.response.business.CategoryResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CategoryMapper {
    public Category mapCategoryRequestToCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }

    public CategoryResponse mapCategoryToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .builtIn(category.getBuiltIn())
                .sequence(category.getSequence())
                .build();
    }
}
