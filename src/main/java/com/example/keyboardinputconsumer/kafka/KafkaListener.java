package com.example.keyboardinputconsumer.kafka;

import com.example.keyboardinputconsumer.models.UserDetails;
import com.example.keyboardinputconsumer.services.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@Component
@RequiredArgsConstructor
public class KafkaListener {

    private final UserDetailsService userDetailsService;
    private final Set<UserDetails> cache = new HashSet<>();
    private final int cacheSize = 20;

    @org.springframework.kafka.annotation.KafkaListener(id = "test", topics = "keyboardInput")
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload String value) {
        log.info(String.format("Received: %s:%s", key, value));
        if (cache.size() >= cacheSize) {
            log.info(String.format("Posted: %s", cache.toString()));
            Set<UserDetails> response = postCache(cache);
            log.info(String.format("Saved: %s", response.toString()));
        }
        cache.add(UserDetails.builder().ipAddress(key).message(value).build());
    }

    private Set<UserDetails> postCache(Set<UserDetails> cache) {
        return userDetailsService.postDetails(cache);
    }

}
