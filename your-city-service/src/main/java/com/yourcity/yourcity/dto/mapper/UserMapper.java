package com.yourcity.yourcity.dto.mapper;

import com.yourcity.yourcity.dto.user.UserCreationDto;
import com.yourcity.yourcity.dto.user.UserEditDto;
import com.yourcity.yourcity.dto.user.UserRepresentationDto;
import com.yourcity.yourcity.model.entity.User;
import com.yourcity.yourcity.model.entity.enums.NetworkStatus;
import com.yourcity.yourcity.model.entity.enums.Role;
import com.yourcity.yourcity.model.entity.enums.Status;
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
