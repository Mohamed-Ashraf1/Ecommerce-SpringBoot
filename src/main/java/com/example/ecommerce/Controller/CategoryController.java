package com.example.ecommerce.Controller;

import com.example.ecommerce.Service.CategoryService;
import com.example.ecommerce.entity.Admin;
import com.example.ecommerce.entity.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getSubCategories();
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }
    @PostMapping
    public void createCategory(@RequestBody Category category) {
        categoryService.addNewCategory(category);
    }
    @PutMapping("/{id}")
    public void updateCategory(@RequestBody Category category, @PathVariable int id) {
        categoryService.updateCategory(id,category);

    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategoryById(id);
    }

}
