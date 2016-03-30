package com.tsystems.javaschool.milkroad.dto;

/**
 * Created by Sergey on 24.02.2016.
 */
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
