package com.learning.database.service;

import com.learning.database.model.Product;
import com.learning.database.repositories.ProductRepository;
import com.learning.database.payload.ProductDTO;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file ProductServiceImpl
 */

@Service
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductDTO createProduct(ProductDTO productDTO) {
    Product product = new Product();
    product.setProductName(productDTO.getProductName());

    product.setDescription(productDTO.getDescription());
    product.setQuantity(productDTO.getQuantity());
    product.setPrice(productDTO.getPrice());
    product.setDiscount(productDTO.getDiscount());
    product.setSpecialPrice(productDTO.getSpecialPrice());

    Product newProduct = productRepository.save(product);

    ProductDTO postResponse = new ProductDTO();
    postResponse.setProductName(newProduct.getProductName());
    postResponse.setDescription(newProduct.getDescription());
    postResponse.setQuantity(newProduct.getQuantity());
    postResponse.setPrice(newProduct.getPrice());
    postResponse.setDiscount(newProduct.getDiscount());
    postResponse.setSpecialPrice(newProduct.getSpecialPrice());

    return postResponse;
  }
}
