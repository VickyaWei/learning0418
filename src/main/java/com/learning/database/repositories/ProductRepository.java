package com.learning.database.repositories;

import com.learning.database.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file ProductRepository
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Override
  Product save(Product product);
}
