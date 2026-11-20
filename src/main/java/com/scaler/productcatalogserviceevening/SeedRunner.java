package com.scaler.productcatalogserviceevening;

import com.scaler.productcatalogserviceevening.models.*;
import com.scaler.productcatalogserviceevening.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SeedRunner implements CommandLineRunner {
    private final ProductRepository productRepo;
    public SeedRunner(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("SeedRunner starting...");
        System.out.println("Products before: " + productRepo.count());

        Category category = new Category();
        category.setName("beauty");

        Product product = new Product();
        product.setName("Essence Mascara Lash Princess");
        product.setDescription("A popular mascara");
        product.setPrice(new BigDecimal("9.99"));
        product.setCategory(category);

//        Product saved = productRepo.save(product);
//        System.out.println("Saved with id " + saved.getId());
//
//        Product found = productRepo.findById(saved.getId()).orElseThrow();
//        System.out.println("Found: " + found.getName() + " costs " + found.getPrice()
//                + " in " + found.getCategory().getName());
    }
}
