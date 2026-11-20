package com.scaler.productcatalogserviceevening.controllers;

import com.scaler.productcatalogserviceevening.dtos.CategoryDto;
import com.scaler.productcatalogserviceevening.dtos.ProductDto;
import com.scaler.productcatalogserviceevening.dtos.ProductPitchDto;
import com.scaler.productcatalogserviceevening.models.Category;
import com.scaler.productcatalogserviceevening.models.Product;
import com.scaler.productcatalogserviceevening.services.IProductService;
import com.scaler.productcatalogserviceevening.services.PitchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    IProductService productService;
    PitchService pitchService;
    IProductService supplierService;

    public ProductController(IProductService productService, PitchService pitchService,@Qualifier("dummyJsonAPIService") IProductService supplierService) {
        this.productService = productService;
        this.pitchService = pitchService;
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id, HttpEntity<Object> httpEntity){

        if(id < 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(id);
        if(product == null){
//            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
//        return new ResponseEntity<>(from(product), HttpStatus.OK);
        return ResponseEntity.ok(from(product));
    }

    @GetMapping("/{id}/pitch")
    public ResponseEntity<ProductPitchDto> getProductPitchById(@PathVariable("id") Long id, HttpEntity<Object> httpEntity){
        if(id < 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(id);
        if(product == null){
//            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
        String pitch  = pitchService.generatePitch(product);
        ProductPitchDto productPitchDto = new ProductPitchDto();
        productPitchDto.setProductName(product.getName());
        productPitchDto.setProductId(product.getId());
        productPitchDto.setPitchNotification(pitch);
//        return new ResponseEntity<>(from(product), HttpStatus.OK);
        return ResponseEntity.ok(productPitchDto);
    }


    // Find price between some range minPrice and MaxPrice
    // Pageable - combination of page, size, sort
    @GetMapping("/search")
    public PagedModel<ProductDto> searchAllProducts(@RequestParam(required = false) BigDecimal minPrice,
                                              @RequestParam(required = false) BigDecimal maxPrice,
                                              Pageable pageable){
        Page<Product> products = productService.searchProduct(minPrice, maxPrice, pageable);
        Page<ProductDto> productDtos = products.map(product -> from(product));
        return new PagedModel<>(productDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto){
        productDto.setId(id);
        Product product = productService.updateProduct(from(productDto));
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(from(product));
    }


    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        Product product = productService.createProduct(from(productDto));
        return ResponseEntity.ok(from(product));
    }

    @PostMapping("/import")
    public String importProducts(){
        // get all products from supplier and add it in product service
        List<Product> products = supplierService.getAllProducts();
        int count = 0;
        for(Product product : products){
            count++;
            productService.createProduct(product);
        }
        return "All " + count  + "products have been imported";
    }

    private ProductDto from(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());

        if(product.getCategory()!=null){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());

            productDto.setCategory(categoryDto);
        }

        return productDto;
    }

    private Product from(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());

        if(productDto.getCategory() != null){
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }
}
