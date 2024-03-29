package com.yourcity.user.userservice.repository;

import com.yourcity.domain.domain.model.entity.User;
import com.yourcity.domain.domain.model.enums.NetworkStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByNetworkStatus(NetworkStatus networkStatus, Pageable pageable);
}
