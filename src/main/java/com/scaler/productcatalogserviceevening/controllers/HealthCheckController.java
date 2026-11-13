package com.scaler.productcatalogserviceevening.controllers;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public String check(){
        return "OK";
    }
}
