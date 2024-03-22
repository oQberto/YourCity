package com.yourcity.user.userservice.unit.service;

import com.yourcity.domain.domain.model.entity.User;
import com.yourcity.domain.domain.model.enums.NetworkStatus;
import com.yourcity.domain.domain.model.enums.Role;
import com.yourcity.domain.domain.model.enums.Status;
import com.yourcity.user.userservice.model.dto.UserCreationDto;
import com.yourcity.user.userservice.model.dto.UserEditDto;
import com.yourcity.user.userservice.model.dto.UserRepresentationDto;
import com.yourcity.user.userservice.model.mapper.UserMapper;
import com.yourcity.user.userservice.repository.UserRepository;
import com.yourcity.user.userservice.service.exception.EntityCreationException;
import com.yourcity.user.userservice.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private User user;
    private UserEditDto userEditDto;
    private UserCreationDto userCreationDto;
    private UserRepresentationDto userRepresentationDto;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("email")
                .password("password")
                .networkStatus(NetworkStatus.ONLINE)
                .status(Status.ACTIVE)
                .role(Role.ADMIN)
                .build();

        userEditDto = UserEditDto.builder()
                .id(1L)
                .email("newEmail")
                .password("password")
                .build();

        userCreationDto = UserCreationDto.builder()
                .email("email")
                .password("password")
                .build();

        userRepresentationDto = UserRepresentationDto.builder()
                .id(1L)
                .email("email")
                .networkStatus(NetworkStatus.ONLINE)
                .status(Status.ACTIVE)
                .role(Role.ADMIN)
                .build();
    }

    @Test
    public void createUser_whenPassedCorrectDto_shouldReturnCreatedUser() {

        when(userMapper.mapToUser(userCreationDto)).thenReturn(user);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userMapper.mapToUserRepresentationDto(user)).thenReturn(userRepresentationDto);

        UserRepresentationDto actualResult = userService.createUser(userCreationDto);
        InOrder inOrder = inOrder(userRepository, userMapper);

        inOrder.verify(userMapper).mapToUser(userCreationDto);
        inOrder.verify(userRepository).saveAndFlush(user);
        inOrder.verify(userMapper).mapToUserRepresentationDto(user);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(user.getId());
    }

    @Test
    public void createUser_whenSaveAndFlushReturnsEmpty_shouldThrowEntityCreationException() {

        when(userMapper.mapToUser(userCreationDto)).thenReturn(user);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(null);

        assertThatThrownBy(() -> userService.createUser(userCreationDto))
                .isInstanceOf(EntityCreationException.class)
                .hasMessageContaining(UserServiceImpl.USER_CREATION);

        verify(userMapper).mapToUser(any(UserCreationDto.class));
        verify(userRepository).saveAndFlush(any(User.class));
        verify(userMapper, times(0)).mapToUserRepresentationDto(any(User.class));
    }

    @Test
    public void getUserById_whenUserExists_shouldReturnUser() {
        var id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.mapToUserRepresentationDto(user)).thenReturn(userRepresentationDto);

        UserRepresentationDto actualResult = userService.getUserById(id);
        InOrder inOrder = inOrder(userRepository, userMapper);

        inOrder.verify(userRepository).findById(anyLong());
        inOrder.verify(userMapper).mapToUserRepresentationDto(any(User.class));

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(id);
    }

    @Test
    public void getUserById_whenUserDoesNotExist_shouldThrowEntityNotFoundException() {

        when(userRepository.findById(Long.MIN_VALUE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(Long.MIN_VALUE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(
                        String.format(UserServiceImpl.USER_NOT_FOUND, Long.MIN_VALUE)
                );
    }

    @Test
    public void getUsersByNetworkStatus_whenUsersWithPassedNetworkStatusExist_shouldReturnListOfUsers() {
        Page<User> users = new PageImpl<>(getStreets(10));
        Pageable pageable = Pageable.unpaged();

        when(userRepository.findAllByNetworkStatus(NetworkStatus.ONLINE, pageable)).thenReturn(users);
        when(userMapper.mapToUserRepresentationDto(any(User.class))).thenReturn(UserRepresentationDto.builder().build());

        List<UserRepresentationDto> actualResult = userService.getUsersByNetworkStatus(NetworkStatus.ONLINE, pageable);

        assertThat(actualResult).hasSize(10);

        verify(userRepository).findAllByNetworkStatus(NetworkStatus.ONLINE, pageable);
        verify(userMapper, times(10)).mapToUserRepresentationDto(any(User.class));
    }

    @Test
    public void updateStreet() {

        var updatedUser = User.builder()
                .id(userEditDto.getId())
                .email(userEditDto.getEmail())
                .password(userEditDto.getPassword())
                .build();
        var userRepresentationDto = UserRepresentationDto.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .build();

        when(userRepository.findById(userEditDto.getId())).thenReturn(Optional.of(user));
        when(userMapper.updateUser(userEditDto, user)).thenReturn(updatedUser);
        when(userRepository.saveAndFlush(updatedUser)).thenReturn(updatedUser);
        when(userMapper.mapToUserRepresentationDto(updatedUser)).thenReturn(userRepresentationDto);

        UserRepresentationDto actualResult = userService.updateUser(userEditDto);
        InOrder inOrder = inOrder(userRepository, userMapper);

        inOrder.verify(userRepository).findById(anyLong());
        inOrder.verify(userMapper).updateUser(userEditDto, user);
        inOrder.verify(userRepository).saveAndFlush(updatedUser);
        inOrder.verify(userMapper).mapToUserRepresentationDto(updatedUser);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(updatedUser.getId());
    }

    @Test
    @Disabled
    public void deleteStreet_whenStreetExists_shouldInvokeDeleteMethod() {
        //TODO: create a test
    }

    @Test
    @Disabled
    public void deleteStreet_whenStreetDoesNotExist_shouldNotInvokeDeleteMethod() {
        //TODO: create a test
    }

    private List<User> getStreets(int amount) {
        List<User> places = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            places.add(User.builder()
                    .id((long) i)
                    .build());
        }

        return places;
    }
}
