package com.yourcity.yourcity.service.impl;

import com.yourcity.yourcity.dto.mapper.UserMapper;
import com.yourcity.yourcity.dto.user.UserCreationDto;
import com.yourcity.yourcity.dto.user.UserEditDto;
import com.yourcity.yourcity.dto.user.UserRepresentationDto;
import com.yourcity.yourcity.model.entity.User;
import com.yourcity.yourcity.model.entity.enums.NetworkStatus;
import com.yourcity.yourcity.model.entity.enums.Status;
import com.yourcity.yourcity.repository.UserRepository;
import com.yourcity.yourcity.service.UserService;
import com.yourcity.yourcity.service.exception.EntityCreationException;
import com.yourcity.yourcity.service.exception.EntityUpdateException;
import com.yourcity.yourcity.service.exception.user.UserDeletionException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    public static final String USER_NOT_FOUND = "User with id \"%d\" not found";
    public static final String USER_NOT_EXISTS = "User with id \"%d\" doesn't exist";
    public static final String USER_CREATION = "Couldn't create a user.";
    public static final String USER_DELETION = "Couldn't delete a user with id \"%d\".";
    public static final String USER_UPDATE = "Couldn't update a user.";

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    @Transactional
    public UserRepresentationDto createUser(UserCreationDto dto) {
        return Optional.of(dto)
                .map(userMapper::mapToUser)
                .map(userRepository::saveAndFlush)
                .map(userMapper::mapToUserRepresentationDto)
                .orElseThrow(() -> new EntityCreationException(USER_CREATION));
    }

    @Override
    public UserRepresentationDto getUserById(Long id) {
        return userRepository
                .findById(id)
                .map(userMapper::mapToUserRepresentationDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(USER_NOT_FOUND, id)
                        )
                );
    }

    @Override
    @Transactional
    public UserRepresentationDto updateUser(UserEditDto dto) {
        return userRepository
                .findById(dto.getId())
                .map(user -> userMapper.updateUser(dto, user))
                .map(userRepository::saveAndFlush)
                .map(userMapper::mapToUserRepresentationDto)
                .orElseThrow(() -> new EntityUpdateException(USER_UPDATE));
    }

    @Override
    @Transactional
    public Long deleteUser(Long id) {
        isUserExists(id);

        return userRepository
                .findById(id)
                .map(this::setDeletedStatus)
                .map(User::getId)
                .orElseThrow(() ->
                        new UserDeletionException(
                                String.format(USER_DELETION, id)
                        )
                );
    }

    @Override
    public List<UserRepresentationDto> getUsersByNetworkStatus(NetworkStatus networkStatus, Pageable pageable) {
        return userRepository
                .findAllByNetworkStatus(networkStatus, pageable)
                .stream()
                .map(userMapper::mapToUserRepresentationDto)
                .toList();
    }

    private void isUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format(USER_NOT_EXISTS, id)
            );
        }
    }

    private User setDeletedStatus(User user) {
        user.setStatus(Status.DELETED);
        user.setNetworkStatus(NetworkStatus.OFFLINE);
        return user;
    }
}
