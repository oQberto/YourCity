package com.yourcity.event.eventservice.config;

import com.yourcity.address.addressservice.model.mapper.*;
import com.yourcity.user.userservice.model.mapper.UserMapper;
import com.yourcity.user.userservice.model.mapper.UserMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public AddressMapper addressMapper() {
        return new AddressMapperImpl();
    }

    @Bean
    public CountryMapper countryMapper() {
        return new CountryMapperImpl();
    }

    @Bean
    public CityMapper cityMapper() {
        return new CityMapperImpl();
    }

    @Bean
    public StreetMapper streetMapper() {
        return new StreetMapperImpl();
    }
}
