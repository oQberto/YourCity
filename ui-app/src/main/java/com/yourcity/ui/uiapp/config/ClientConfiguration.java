package com.yourcity.ui.uiapp.config;

import com.yourcity.ui.uiapp.client.impl.UserRestClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public UserRestClientImpl userClient() {
        return new UserRestClientImpl(RestClient.builder()
                .baseUrl("http://localhost:8083")
                .build());
    }
}
