package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.payload.mapper.CategoryMapper;
import com.project.librarymanagement.payload.request.business.CategoryRequest;
import com.project.librarymanagement.payload.response.business.CategoryResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.repository.business.CategoryRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final MethodHelper methodHelper;

    public ResponseMessage<CategoryResponse> saveCategory(CategoryRequest categoryRequest) {
        methodHelper.isCategoryExistByName(categoryRequest.getName());
        Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest);
        category.setSequence(returnMaxSequence() + 1);
        category.setBuiltIn(true);
        Category savedCategory = categoryRepository.save(category);
        return ResponseMessage.<CategoryResponse>builder()
                .message("Category created successfully")
                .returnBody(categoryMapper.mapCategoryToCategoryResponse(savedCategory))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private int returnMaxSequence() {
        List<Category> categories = categoryRepository.findAll();
        List<Integer> sequenceList = new ArrayList<>();
        if (categories.isEmpty()) {
            return 0;
        } else {
            for (Category category : categories) {
                sequenceList.add(category.getSequence());
            }
            return sequenceList.stream().max(Integer::compare).get();
        }
    }

}
