package com.trendyol.interview.model;

import lombok.Data;

@Data
public class Category {

  private String title;
  private Category parentCategory;

  public Category(String title) {
    this.title = title;
  }
}
