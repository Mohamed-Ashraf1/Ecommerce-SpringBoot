package com.example.ecommerce.Service;

import com.example.ecommerce.entity.Admin;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class CategoryService {

    CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository)
    {
            this.categoryRepository = categoryRepository;
    }


//    public List<Category> getParentCategories() throws RuntimeException
//    {
//        return categoryRepository.getParentCategories();
//    }
    public List<Category> getSubCategories() throws RuntimeException
    {
        List<Category> subCategories = categoryRepository.findAll();
        System.out.println("size of caegories = "+subCategories.size());
        // somehow it gets all associations (ignoring that it's lazy fetch) and make it recursive so i filtered it
        subCategories.forEach((category) -> {
            category.setChildCategories(null);
            category.setProducts(null);
        });
        return subCategories.stream().filter((c)->c.getParentCategory()!=null).toList();
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


    public Category getCategoryById(int id) throws RuntimeException {
       return categoryRepository.findById(id).orElse(null);
    }

    public void updateCategory(int id, Category category) {
        Category category1 = categoryRepository.findById(id).orElse(null);
        category1.setName(category.getName());
        category1.setDescription(category.getDescription());
        category1.setParentCategory(category.getParentCategory());
        categoryRepository.save(category1);
    }

    public void deleteCategoryById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
