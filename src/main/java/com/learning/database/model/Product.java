package com.learning.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file Product
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@ToString
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @NotBlank
  @Size(min = 3, message = "Name must be at least 3 characters long")
  private String productName;

  @NotBlank
  @Size(min = 10, message = "Description must be at least 10 characters long")
  private String description;

  private Integer quantity;
  private Double price;
  private Double discount;
  private Double specialPrice;
}
