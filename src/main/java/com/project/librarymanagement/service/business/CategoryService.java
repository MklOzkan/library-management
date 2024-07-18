package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.payload.mapper.CategoryMapper;
import com.project.librarymanagement.payload.messages.SuccessMessages;
import com.project.librarymanagement.payload.request.business.CategoryRequest;
import com.project.librarymanagement.payload.response.business.CategoryResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.repository.business.CategoryRepository;
import com.project.librarymanagement.service.helper.MethodHelper;
import com.project.librarymanagement.service.helper.PageableHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PageableHelper pageableHelper;

    public ResponseMessage<CategoryResponse> saveCategory(CategoryRequest categoryRequest, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        //check if user is an Admin
        methodHelper.isRoleAdmin(authenticatedUser);
        methodHelper.isCategoryExistByName(categoryRequest.getName());
        Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest);
        category.setSequence(returnMaxSequence() + 1);
        category.setBuiltIn(true);
        Category savedCategory = categoryRepository.save(category);
        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_SAVE)
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

    public Page<CategoryResponse> findCategoriesByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::mapCategoryToCategoryResponse);
    }

    public ResponseMessage<CategoryResponse> findCategoryById(Long id) {
        Category category = methodHelper.getCategoryById(id);
        return ResponseMessage.<CategoryResponse>builder()
                .returnBody(categoryMapper.mapCategoryToCategoryResponse(category))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<CategoryResponse> updateCategory(Long id, CategoryRequest categoryRequest, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        //check if user is an Admin
        methodHelper.isRoleAdmin(authenticatedUser);
        Category category = methodHelper.getCategoryById(id);
        if (!category.getName().equals(categoryRequest.getName())) {
            methodHelper.isCategoryExistByName(categoryRequest.getName());
        }
        category.setName(categoryRequest.getName());
        Category updatedCategory = categoryRepository.save(category);
        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_UPDATE)
                .returnBody(categoryMapper.mapCategoryToCategoryResponse(updatedCategory))
                .build();
    }

    public ResponseMessage<CategoryResponse> deleteCategoryById(Long id, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authenticatedUser = methodHelper.loadUserByEmail(email);
        //check if user is an Admin
        methodHelper.isRoleAdmin(authenticatedUser);
        Category category = methodHelper.getCategoryById(id);
        categoryRepository.deleteById(id);
        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_DELETE)
                .returnBody(categoryMapper.mapCategoryToCategoryResponse(category))
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
