package com.yourcity.yourcity.repository;

import com.yourcity.domain.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Page<Address> findAllByCountryName(String name, Pageable pageable);

    Page<Address> findAllByCityName(String name, Pageable pageable);

    Page<Address> findAllByStreetName(String name, Pageable pageable);
}
