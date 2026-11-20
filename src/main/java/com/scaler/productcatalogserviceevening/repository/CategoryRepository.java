package com.scaler.productcatalogserviceevening.repository;

import com.scaler.productcatalogserviceevening.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
