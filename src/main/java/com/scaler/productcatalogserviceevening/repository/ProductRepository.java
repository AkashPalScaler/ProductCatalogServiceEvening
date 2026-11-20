package com.scaler.productcatalogserviceevening.repository;

import com.scaler.productcatalogserviceevening.models.Product;
import com.scaler.productcatalogserviceevening.models.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndState(Long id, State state);

    Page<Product> findAllByState(State state, Pageable pageable);

    Page<Product> findAllByPriceBetweenAndState(BigDecimal minPrice, BigDecimal maxPrice, State state, Pageable pageable);
}

// what + conditionsStartBy +
// "find" + "By" + "Id"/"State"/"PriceBetween"
