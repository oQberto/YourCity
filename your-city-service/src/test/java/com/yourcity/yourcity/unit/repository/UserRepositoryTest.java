package com.yourcity.yourcity.unit.repository;

import com.yourcity.yourcity.YourCityServiceApplication;
import com.yourcity.yourcity.model.entity.User;
import com.yourcity.yourcity.model.entity.enums.NetworkStatus;
import com.yourcity.yourcity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@Sql({
        "classpath:sql/test-data-clear.sql",
        "classpath:sql/test-data-insert.sql"
})
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = YourCityServiceApplication.class)
public class UserRepositoryTest {
    UserRepository userRepository;

    @Test
    public void findAllByNetworkStatus_ifNetworkStatusExists_shouldReturnListOfUsers() {
        var networkStatus = NetworkStatus.ONLINE;
        var pageableFiveElements = PageRequest.of(0, 5);
        var pageableTwentyElements = PageRequest.of(0, 20);

        Page<User> actualFiveUsers = userRepository.findAllByNetworkStatus(networkStatus, pageableFiveElements);
        Page<User> actualFifteenUsers = userRepository.findAllByNetworkStatus(networkStatus, pageableTwentyElements);

        assertThat(actualFiveUsers).hasSize(5);
        assertThat(actualFifteenUsers).hasSize(15);
    }

    @Test
    public void findAllByNetworkStatus_ifNetworkStatusDoesNotExist_shouldReturnEmptyList() {
        NetworkStatus anyNetworkStatus = null;
        var anyPageable = Pageable.unpaged();

        Page<User> actualUsers = userRepository.findAllByNetworkStatus(anyNetworkStatus, anyPageable);

        assertThat(actualUsers).isEmpty();
    }
}