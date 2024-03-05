package com.yourcity.yourcity.repository;

import com.yourcity.yourcity.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByRoomNumber(Short number);

    Optional<Address> findByBuildingNumber(Short number);

    List<Address> findAllByCountryName(String name);

    List<Address> findAllByCityName(String name);

    List<Address> findAllByStreetName(String name);
}
