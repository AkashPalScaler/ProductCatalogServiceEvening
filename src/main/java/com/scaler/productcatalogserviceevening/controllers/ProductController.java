package com.scaler.productcatalogserviceevening.controllers;

import com.scaler.productcatalogserviceevening.dtos.CategoryDto;
import com.scaler.productcatalogserviceevening.dtos.ProductDto;
import com.scaler.productcatalogserviceevening.dtos.ProductPitchDto;
import com.scaler.productcatalogserviceevening.models.Product;
import com.scaler.productcatalogserviceevening.services.IProductService;
import com.scaler.productcatalogserviceevening.services.PitchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    IProductService productService;
    PitchService pitchService;

    public ProductController(IProductService productService, PitchService pitchService) {
        this.productService = productService;
        this.pitchService = pitchService;
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

    @GetMapping
    public List<ProductDto> getAllProducts(){
        Product product1 = new Product();
        product1.setId(10l);
        product1.setName("Iphone 17");
        product1.setDescription("Iphone 17 description");

        Product product2 = new Product();
        product2.setId(11l);
        product2.setName("Iphone 18");
        product2.setDescription("Iphone 18 description");
        return List.of(from(product1),from(product2));
    }
    @PostMapping
    public ProductDto createProduct(@RequestBody Product product){
        product.setId(12l);
        return  from(product);
    }

    private ProductDto from(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setName(product.getCategory().getName());
        categoryDto.setDescription(product.getCategory().getDescription());

        productDto.setCategoryDto(categoryDto);

        return productDto;
    }
}
