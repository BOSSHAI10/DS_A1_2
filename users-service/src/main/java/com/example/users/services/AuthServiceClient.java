package com.example.users.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AuthServiceClient {

    private final RestTemplate restTemplate;

    // Read the URL from application.properties (e.g., auth.service.url=http://auth-service:8083/auth)
    @Value("${auth.service.url:http://auth-service:8083/auth}")
    private String authServiceUrl;

    public AuthServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void deleteCredentials(String email) {
        try {
            // Optional: Forward the Authorization header (JWT)
            HttpHeaders headers = new HttpHeaders();
            String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest().getHeader("Authorization");
            if (token != null) {
                headers.set("Authorization", token);
            }

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // Call DELETE http://auth-service:8083/auth/{email}
            restTemplate.exchange(
                    authServiceUrl + "/" + email,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
        } catch (Exception e) {
            // Log error but don't stop the user deletion?
            // Or throw exception to rollback?
            // For now, we log it.
            System.err.println("Failed to delete credentials for " + email + ": " + e.getMessage());
        }
    }
}