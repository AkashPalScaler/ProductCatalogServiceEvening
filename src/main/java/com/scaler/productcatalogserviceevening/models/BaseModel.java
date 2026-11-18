package com.scaler.productcatalogserviceevening.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id // this is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1, 2, 3 // How to auytomatically add ids
    private Long id;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
