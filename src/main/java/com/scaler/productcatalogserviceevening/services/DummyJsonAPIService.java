package com.scaler.productcatalogserviceevening.services;

import com.scaler.productcatalogserviceevening.dtos.DummyJsonProductDto;
import com.scaler.productcatalogserviceevening.dtos.DummyJsonProductListDto;
import com.scaler.productcatalogserviceevening.models.Category;
import com.scaler.productcatalogserviceevening.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DummyJsonAPIService implements IProductService{
    @Override
    public Page<Product> searchProduct(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }

    private final RestTemplate restTemplate;
    private final String dummyJsonAPIBaseUrl;

    public DummyJsonAPIService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        this.dummyJsonAPIBaseUrl = "https://dummyjson.com";
    }
    @Override
    public Product getProductById(Long id) {
        try{
            ResponseEntity<DummyJsonProductDto> responseEntity =
                    restTemplate.getForEntity(dummyJsonAPIBaseUrl+"/products/{id}", DummyJsonProductDto.class, id);
            if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
                return from(responseEntity.getBody());
            }
            return null;
        }catch (HttpClientErrorException e){
            System.out.println("Error getting product by id " + id + " - " + e.getMessage());
            return null;
        }

    }

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<DummyJsonProductListDto> response = restTemplate.getForEntity(
                "https://dummyjson.com/products?limit=0", DummyJsonProductListDto.class);

        List<Product> products = new ArrayList<>();
        for (DummyJsonProductDto dto : response.getBody().getProducts()) {
            products.add(from(dto));
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    private Product from(DummyJsonProductDto dummyJsonProductDto){
        Product product = new Product();
        product.setId(dummyJsonProductDto.getId());
        product.setName(dummyJsonProductDto.getTitle());
        product.setDescription(dummyJsonProductDto.getDescription());
        product.setPrice(dummyJsonProductDto.getPrice());
        product.setImageUrl(dummyJsonProductDto.getThumbnail());

        Category category = new Category();
        category.setName(dummyJsonProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
// getForEntity - HTTP Entity - Status code, Payload, Headers
// getForObject - Payload(of the HTTP entity)