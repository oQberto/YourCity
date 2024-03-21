package com.yourcity.yourcity.repository;

import com.yourcity.yourcity.model.entity.Street;
import com.yourcity.yourcity.model.entity.enums.Type;
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
