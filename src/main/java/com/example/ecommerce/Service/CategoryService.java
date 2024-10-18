package com.example.ecommerce.Service;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.repository.CategoryRepository;


import java.util.List;

public class CategoryService {

    CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository)
    {
            this.categoryRepository = categoryRepository;
    }


    public List<Category> getParentCategories() throws RuntimeException
    {
        return categoryRepository.getParentCategories();
    }

    public Category addNewCategory(Category category) throws RuntimeException
    {
        try {
            categoryRepository.save(category);
        }catch (Exception e) {

            throw new RuntimeException(e);
        }
        return category;
    }


}
