package com.scaler.productcatalogserviceevening.services;

import com.scaler.productcatalogserviceevening.dtos.DummyJsonProductDto;
import com.scaler.productcatalogserviceevening.models.Category;
import com.scaler.productcatalogserviceevening.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Primary
@Service
public class DummyJsonAPIService implements IProductService{
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
    public Product getAllProducts() {
        return null;
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