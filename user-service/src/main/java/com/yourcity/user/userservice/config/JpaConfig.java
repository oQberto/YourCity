package com.yourcity.user.userservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {
        "com.yourcity.domain.domain.model.entity"
})
public class JpaConfig {
}
