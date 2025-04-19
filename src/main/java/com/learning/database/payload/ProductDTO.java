package com.learning.database.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file ProductDTO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
  private String productName;
  private String image;
  private String description;
  private Integer quantity;
  private Double price;
  private Double discount;
  private Double specialPrice;
}
