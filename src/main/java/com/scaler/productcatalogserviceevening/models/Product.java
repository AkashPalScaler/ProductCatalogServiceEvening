package com.scaler.productcatalogserviceevening.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String name;
    private String description;
    @Column(precision = 9, scale = 2)
    private BigDecimal price;
    private String imageUrl;
    // Product M:1 Category
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
    private Double stock;
}
// Cardinality -
// // Product M:1 Category
// Product - id, name , price,...., category_id
// Category - id , name, desc

// Mapping table - category_products??

// MERGE, DETACH - bean lifecycle