package com.tsystems.javaschool.milkroad.dto;

import java.math.BigDecimal;

/**
 * Created by Sergey on 14.02.2016.
 */
public class ProductDTO {
    private Long article;
    private UserDTO seller;
    private String name;
    private CategoryDTO category;
    private BigDecimal price;
    private Integer count;
    private String description;

    public Long getArticle() {
        return article;
    }

    public void setArticle(final Long article) {
        this.article = article;
    }

    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(final UserDTO seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(final CategoryDTO category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
