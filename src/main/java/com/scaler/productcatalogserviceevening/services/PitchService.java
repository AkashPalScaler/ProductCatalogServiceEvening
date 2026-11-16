package com.scaler.productcatalogserviceevening.services;

import com.scaler.productcatalogserviceevening.dtos.GroqChatResponseDto;
import com.scaler.productcatalogserviceevening.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.ObjenesisHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PitchService {
    private final static String API = "https://api.groq.com/openai/v1/chat/completions";
    private final static String MODEL = "openai/gpt-oss-20b";
    private final static String REASONING_EFFORT = "low";
    private final static Integer MAX_TOKEN = 300;
    private final static String SYSTEM_PROMPT = "You are a marketing copywriter for ShopNest, an online store. You write short push notifications that make people want to open the app.";


    public final RestTemplate restTemplate;
    public final String apiKey;

    public PitchService(RestTemplateBuilder builder, @Value("${groq.api-key}") String apiKey) {
        this.restTemplate = builder.build();
        this.apiKey = apiKey;
    }

    public String generatePitch(Product product) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object>  body = Map.of(
                    "model",  MODEL,
                    "reasoning_effort", REASONING_EFFORT,
                    "max_completion_tokens" , MAX_TOKEN,
                    "messages", List.of(
                            Map.of("role", "system",
                                    "content", SYSTEM_PROMPT),
                            Map.of("role", "user",
                                    "content", userPromptFormatter(product)
                            )
                    )
            );

            ResponseEntity<GroqChatResponseDto>  responseEntity =
                    restTemplate.postForEntity(API, new HttpEntity<>(body, headers), GroqChatResponseDto.class);

            if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
                return responseEntity.getBody().getChoices().get(0).getMessage().getContent();
            }
            return "dummy pitch";
        }catch (HttpClientErrorException e){
            System.out.println("Error while calling Groq API : " + e.getMessage());
            return "dummy pitch";
        }

    }

    private String userPromptFormatter(Product product) {
        return """
                Write ONE push notification for this product.
                Rules:
                -Maximum 25 words.
                - Mention the price.
                - At most one emoji.
                - No hashtags.
                - Reply with only the notification text.
                
                Product: %s
                Category: %s
                Price: %s
                Details: %s
                """.formatted(product.getName(), product.getCategory().getName(), product.getPrice(), product.getDescription());
    }
}
