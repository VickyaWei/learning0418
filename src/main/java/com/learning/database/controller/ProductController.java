package com.learning.database.controller;

import com.learning.database.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learning.database.payload.ProductDTO;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file ProductController
 */

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
    return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
  }
}
