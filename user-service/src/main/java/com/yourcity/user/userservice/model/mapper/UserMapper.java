package com.yourcity.user.userservice.model.mapper;

import com.yourcity.domain.entity.User;
import com.yourcity.domain.enums.NetworkStatus;
import com.yourcity.domain.enums.Role;
import com.yourcity.domain.enums.Status;
import com.yourcity.user.userservice.model.dto.UserCreationDto;
import com.yourcity.user.userservice.model.dto.UserEditDto;
import com.yourcity.user.userservice.model.dto.UserRepresentationDto;
import org.mapstruct.*;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {
                Status.class,
                Role.class,
                NetworkStatus.class
        }
)
public interface UserMapper {

    UserRepresentationDto mapToUserRepresentationDto(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "networkStatus", expression = "java(NetworkStatus.ONLINE)"),
            @Mapping(target = "role", expression = "java(Role.USER)"),
            @Mapping(target = "isVerified", expression = "java(false)")
    })
    User mapToUser(UserCreationDto dto);

    @Mappings({
            @Mapping(target = "username",conditionExpression = "java(!dto.getUsername().isEmpty())"),
            @Mapping(target = "password",conditionExpression = "java(!dto.getPassword().isEmpty())"),
            @Mapping(target = "email",conditionExpression = "java(!dto.getEmail().isEmpty())"),
    })
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    User updateUser(UserEditDto dto,
                    @MappingTarget User user);
}
