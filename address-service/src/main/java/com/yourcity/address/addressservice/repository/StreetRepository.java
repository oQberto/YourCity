package com.yourcity.address.addressservice.repository;

import com.yourcity.domain.entity.Street;
import com.yourcity.domain.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {

    Optional<Street> findByName(String name);

    Page<Street> findAllByType(Type type, Pageable pageable);
}
