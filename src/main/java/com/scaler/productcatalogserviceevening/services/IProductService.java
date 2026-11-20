package com.scaler.productcatalogserviceevening.services;

import com.scaler.productcatalogserviceevening.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    Page<Product> searchProduct(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
// Put (replaces entire object with new values being sent) and Patch (updates only the fields sent)
// put {name:"sfkbs", desc:"svfksfb"} - except for name and desc every other value becomes null || patch {name:"sfkbs", desc:"svfksfb"}  - only name and desc gets replaced