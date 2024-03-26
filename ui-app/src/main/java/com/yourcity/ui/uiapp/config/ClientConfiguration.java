package com.yourcity.ui.uiapp.config;

import com.yourcity.ui.uiapp.client.impl.UserRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public UserRestClientImpl userClient(
            @Value("${user.service.url:http://localhost:8080}") String url
    ) {
        return new UserRestClientImpl(RestClient.builder()
                .baseUrl(url)
                .build());
    }
}
