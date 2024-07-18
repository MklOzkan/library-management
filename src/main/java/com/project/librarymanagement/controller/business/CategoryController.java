package com.project.librarymanagement.controller.business;

import com.project.librarymanagement.payload.request.business.CategoryRequest;
import com.project.librarymanagement.payload.response.business.CategoryResponse;
import com.project.librarymanagement.payload.response.business.ResponseMessage;
import com.project.librarymanagement.service.business.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/save")
    public ResponseMessage<CategoryResponse> saveCategory(@RequestBody @Valid CategoryRequest categoryRequest, HttpServletRequest httpServletRequest) {
        return categoryService.saveCategory(categoryRequest, httpServletRequest);
    }

    @GetMapping
    public Page<CategoryResponse> findCategoriesByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type) {
        return categoryService.findCategoriesByPage(page, size, sort, type);
    }

    @GetMapping("/{id}")
    public ResponseMessage<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/{id}")
    public ResponseMessage<CategoryResponse> updateCategoryById(@PathVariable Long id,
            @RequestBody @Valid CategoryRequest categoryRequest, HttpServletRequest httpServletRequest) {
        return categoryService.updateCategory(id, categoryRequest, httpServletRequest);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @DeleteMapping("/{id}")
    public ResponseMessage<CategoryResponse> deleteCategoryById(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return categoryService.deleteCategoryById(id, httpServletRequest);
    }

}
