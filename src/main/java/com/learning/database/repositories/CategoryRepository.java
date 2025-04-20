package com.learning.database.repositories;

import com.learning.database.model.Category;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file CategoryRepository
 */
public interface CategoryRepository extends MongoRepository<Category, String> {
  List<Category> findByName(String name);

}
