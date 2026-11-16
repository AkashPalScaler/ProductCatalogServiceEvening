package com.scaler.productcatalogserviceevening.services;

import com.scaler.productcatalogserviceevening.models.Product;
import org.springframework.stereotype.Service;

@Service("mockAPI")
public class MockJsonAPIService implements IProductService {
    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Product getAllProducts() {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }
}

// Break till : 10:11 PM
