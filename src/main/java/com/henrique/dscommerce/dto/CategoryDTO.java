package com.henrique.dscommerce.dto;

import com.henrique.dscommerce.entities.Category;

public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category category) {
        id = category.getId();
        name = category.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}