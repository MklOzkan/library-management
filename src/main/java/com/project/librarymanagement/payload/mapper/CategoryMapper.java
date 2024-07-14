package com.project.librarymanagement.payload.mapper;

import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.payload.request.business.CategoryRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CategoryMapper {
    public Category mapCategoryRequestToCategory (Long id, Category category, boolean builtin, int seq  )
    {
        return Category.builder()
                .id(id)
                .categoryName(category.getCategoryName())
                .builtIn(builtin)
                .sequence(seq)
                .build();
    }

    public CategoryRequest mapCategoryToCategoryRequest(Category category)
    {
        return CategoryRequest.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .builtIn(category.getBuiltIn())
                .sequence(category.getSequence())
                .build();
    }
}
