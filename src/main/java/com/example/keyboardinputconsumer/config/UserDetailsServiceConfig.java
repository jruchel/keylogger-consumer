package com.example.keyboardinputconsumer.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("user-details-service")
@Data
@NoArgsConstructor
public class UserDetailsServiceConfig {

    private String host;
    private int port;

}
