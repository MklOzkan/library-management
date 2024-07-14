package com.project.librarymanagement.service.business;

import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.repository.business.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        categoryRepository.save(category);
        return category;
    }

}
