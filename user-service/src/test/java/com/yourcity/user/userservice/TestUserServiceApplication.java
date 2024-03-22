package com.yourcity.user.userservice;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestUserServiceApplication {
    private static final String IMAGE_VERSION = "postgres:15.4";
    private static final String PROPERTY_NAME = "spring.datasource.url";
    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>(IMAGE_VERSION);

    @BeforeAll
    static void runContainer() {
        CONTAINER.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add(PROPERTY_NAME, CONTAINER::getJdbcUrl);
    }

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return CONTAINER;
    }

    public static void main(String[] args) {
        SpringApplication.from(UserServiceApplication::main).with(TestUserServiceApplication.class).run(args);
    }

}
