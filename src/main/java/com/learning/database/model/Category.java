package com.learning.database.model;

import org.springframework.data.annotation.Id;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file Category
 */
@Document(collection = "categories")
public class Category {

  @Id
  private String id;
  private String name;
  private String description;
  private Date createdAt;
  private boolean active;

  public Category() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "Category{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", createdAt=" + createdAt +
        ", active=" + active +
        '}';
  }
}
