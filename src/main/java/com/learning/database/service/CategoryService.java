package com.learning.database.service;

import com.learning.database.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file CategoryService
 */


public interface CategoryService {
  // Create a new category
  Category createCategory(Category category);

  // Get all categories
  List<Category> getAllCategories();

  // Get category by ID
  Optional<Category> getCategoryById(String id);

  // Get categories by name
  List<Category> getCategoriesByName(String name);

  // Update category
  Category updateCategory(String id, Category categoryDetails);

  // Delete category
  boolean deleteCategory(String id);

}
