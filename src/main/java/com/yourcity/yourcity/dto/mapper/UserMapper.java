package com.yourcity.yourcity.dto.mapper;

import com.yourcity.yourcity.dto.user.UserCreationDto;
import com.yourcity.yourcity.dto.user.UserDto;
import com.yourcity.yourcity.dto.user.UserEditDto;
import com.yourcity.yourcity.model.entity.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto mapToUserDto(User user);

    @Mappings({
            @Mapping(target = "events", conditionExpression = "java(new ArrayList())"),
            @Mapping(target = "chats", conditionExpression = "java(new ArrayList())")
    })
    User mapToUser(UserCreationDto dto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "networkStatus", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "ownedEvent", ignore = true),
            @Mapping(target = "events", ignore = true),
            @Mapping(target = "chats", ignore = true),
            @Mapping(target = "username",conditionExpression = "java(!dto.getUsername().isEmpty())"),
            @Mapping(target = "password",conditionExpression = "java(!dto.getPassword().isEmpty())"),
            @Mapping(target = "email",conditionExpression = "java(!dto.getEmail().isEmpty())"),
    })
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    User updateUser(UserEditDto dto,
                    @MappingTarget User user);
}
