package com.learning.database.service;

import org.springframework.stereotype.Service;
import com.learning.database.payload.ProductDTO;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file ProductService
 */

@Service
public interface ProductService {

  ProductDTO createProduct(ProductDTO productDTO);
}
