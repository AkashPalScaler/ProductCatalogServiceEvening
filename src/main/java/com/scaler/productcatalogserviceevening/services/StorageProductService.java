package com.scaler.productcatalogserviceevening.services;

import com.scaler.productcatalogserviceevening.models.Category;
import com.scaler.productcatalogserviceevening.models.Product;
import com.scaler.productcatalogserviceevening.models.State;
import com.scaler.productcatalogserviceevening.repository.CategoryRepository;
import com.scaler.productcatalogserviceevening.repository.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Primary
@Service
public class StorageProductService implements IProductService {


    @Override
    public Product updateProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.findByIdAndState(product.getId(), State.ACTIVE);
        if(optionalProduct.isEmpty()){
            return null;
        }
        Date now = new Date();
        product.setId(product.getId());
        product.setState(State.ACTIVE);
        product.setCreatedAt(product.getCreatedAt());
        product.setUpdatedAt(now);
        product.setCategory(findOrCreateCategory(product.getCategory()));
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public Page<Product> searchProduct(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if(minPrice != null && maxPrice != null){
            return productRepository.findAllByPriceBetweenAndState(minPrice, maxPrice, State.ACTIVE, pageable);
        }
        return productRepository.findAllByState(State.ACTIVE, pageable);
    }

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public StorageProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findByIdAndState(id, State.ACTIVE).orElse(null);

    }


    // Not using anymore
    @Override
    public List<Product> getAllProducts() {
//        return productRepository.findAllByState(State.ACTIVE);
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        Date now = new Date();
        product.setId(null);
        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        product.setState(State.ACTIVE);
        product.setCategory(findOrCreateCategory(product.getCategory()));
        product = productRepository.save(product);
        return product;
    }



    public Category findOrCreateCategory(Category category) {
        Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }

        Category newCategory = new Category();
        newCategory.setId(null);
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        Date now = new Date();
        newCategory.setState(State.ACTIVE);
        newCategory.setCreatedAt(now);
        newCategory.setUpdatedAt(now);

        return categoryRepository.save(newCategory);
    }

}
// findOrCreate


// SOFT DELETE - INACTIVE - HW
// Break till 10:28 PM