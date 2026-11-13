package com.scaler.productcatalogserviceevening.controllers;

import com.scaler.productcatalogserviceevening.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        Product product = new Product();
        product.setId(id);
        product.setName("Iphone 17");
        product.setDescription("Iphone 17 description");
        return product;
    }
    @GetMapping
    public List<Product> getAllProducts(){
        Product product1 = new Product();
        product1.setId(10l);
        product1.setName("Iphone 17");
        product1.setDescription("Iphone 17 description");

        Product product2 = new Product();
        product2.setId(11l);
        product2.setName("Iphone 18");
        product2.setDescription("Iphone 18 description");
        return List.of(product1,product2);
    }
    @PostMapping
    public Product createProduct(@RequestBody Product product){
        product.setId(12l);
        return  product;
    }
}
