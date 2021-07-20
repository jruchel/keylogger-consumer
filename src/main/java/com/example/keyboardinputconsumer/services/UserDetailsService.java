package com.example.keyboardinputconsumer.services;

import com.example.keyboardinputconsumer.config.UserDetailsServiceConfig;
import com.example.keyboardinputconsumer.models.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final RestTemplate restTemplate;
    private final UserDetailsServiceConfig config;

    public Set<UserDetails> postDetails(Set<UserDetails> userDetails) {
        RequestEntity<Set<UserDetails>> requestEntity = RequestEntity.post(constructUrl("user-details/list")).body(userDetails);
        ParameterizedTypeReference<Set<UserDetails>> typeReference = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(requestEntity, typeReference).getBody();
    }

    private String constructUrl(String endpoint) {
        return String.format("http://%s:%s/%s", config.getHost(), config.getPort(), endpoint);
    }
}
