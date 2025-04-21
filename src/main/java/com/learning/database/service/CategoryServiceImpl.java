package com.learning.database.service;

import com.learning.database.model.Category;
import com.learning.database.repositories.CategoryRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file CategoryServiceImpl
 */
@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category createCategory(Category category) {
    if (category.getCreatedAt() == null) {
      category.setCreatedAt(new Date());
    }
    return categoryRepository.save(category);
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public Optional<Category> getCategoryById(String id) {
    return categoryRepository.findById(id);
  }

  @Override
  public List<Category> getCategoriesByName(String name) {
    return categoryRepository.findByName(name);
  }

  @Override
  public Category updateCategory(String id, Category categoryDetails) {
    return categoryRepository.findById(id)
        .map(existingCategory -> {
          // Update fields but preserve id and creation date
          if (categoryDetails.getName() != null) {
            existingCategory.setName(categoryDetails.getName());
          }
          if (categoryDetails.getDescription() != null) {
            existingCategory.setDescription(categoryDetails.getDescription());
          }
          // Only update active status if explicitly changed
          existingCategory.setActive(categoryDetails.isActive());

          return categoryRepository.save(existingCategory);
        })
        .orElse(null);
  }

  @Override
  public boolean deleteCategory(String id) {
    return categoryRepository.findById(id)
        .map(category -> {
          categoryRepository.deleteById(id);
          return true;
        })
        .orElse(false);
  }
}
