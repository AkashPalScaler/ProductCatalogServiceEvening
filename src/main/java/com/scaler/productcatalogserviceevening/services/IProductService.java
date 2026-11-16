package com.scaler.productcatalogserviceevening.services;

import com.scaler.productcatalogserviceevening.models.Product;

public interface IProductService {
    Product getProductById(Long id);
    Product getAllProducts();
    Product createProduct(Product product);
}
