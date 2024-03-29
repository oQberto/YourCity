package com.yourcity.address.addressservice.unit.repository;

import com.yourcity.address.addressservice.TestAddressServiceApplication;
import com.yourcity.address.addressservice.repository.AddressRepository;
import com.yourcity.domain.domain.model.entity.Address;
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

@RequiredArgsConstructor
@Sql({
        "classpath:sql/test-data-clear.sql",
        "classpath:sql/test-data-insert.sql"
})
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = TestAddressServiceApplication.class)
public class AddressRepositoryTest {
    public static final String COUNTRY = "USA";
    public static final String CITY = "New York";
    public static final String STREET = "Broadway";

    AddressRepository addressRepository;

    @Test
    public void findAllByCountryName_whenCountryExists_shouldReturnListOfAddresses() {
        var pageableFiveElements = PageRequest.of(0, 5);
        var pageableHundredElements = PageRequest.of(0, 100);

        Page<Address> actualFiveAddresses = addressRepository.findAllByCountryName(COUNTRY, pageableFiveElements);
        Page<Address> actualThirtyAddresses = addressRepository.findAllByCountryName(COUNTRY, pageableHundredElements);

        assertThat(actualFiveAddresses).hasSize(5);
        assertThat(actualThirtyAddresses).hasSize(10);
    }

    @Test
    public void findAllByCountryName_whenCountryDoesNotExist_shouldReturnEmptyList() {
        var anyCountry = "";
        var anyPageable = Pageable.unpaged();

        Page<Address> actualEmptyAddresses = addressRepository.findAllByCountryName(anyCountry, anyPageable);

        assertThat(actualEmptyAddresses).isEmpty();
    }

    @Test
    public void findAllByCityName_whenCityExists_shouldReturnListOfAddresses() {
        var pageableFiveElements = PageRequest.of(0, 5);
        var pageableHundredElements = PageRequest.of(0, 100);

        Page<Address> actualFiveAddresses = addressRepository.findAllByCityName(CITY, pageableFiveElements);
        Page<Address> actualThirtyAddresses = addressRepository.findAllByCityName(CITY, pageableHundredElements);

        assertThat(actualFiveAddresses).hasSize(5);
        assertThat(actualThirtyAddresses).hasSize(10);
    }

    @Test
    public void findAllByCityName_whenCityDoesNotExist_shouldReturnEmptyList() {
        var anyCity = "";
        var anyPageable = Pageable.unpaged();

        Page<Address> actualEmptyAddresses = addressRepository.findAllByCityName(anyCity, anyPageable);

        assertThat(actualEmptyAddresses).isEmpty();
    }

    @Test
    public void findAllByStreetName_whenStreetExists_shouldReturnListOfAddresses() {
        var pageableFiveElements = PageRequest.of(0, 5);
        var pageableHundredElements = PageRequest.of(0, 100);

        Page<Address> actualFiveAddresses = addressRepository.findAllByStreetName(STREET, pageableFiveElements);
        Page<Address> actualThirtyAddresses = addressRepository.findAllByStreetName(STREET, pageableHundredElements);

        assertThat(actualFiveAddresses).hasSize(5);
        assertThat(actualThirtyAddresses).hasSize(10);
    }

    @Test
    public void findAllByStreetName_whenStreetDoesNotExist_shouldReturnEmptyList() {
        var anyStreet = "";
        var anyPageable = Pageable.unpaged();

        Page<Address> actualEmptyAddresses = addressRepository.findAllByStreetName(anyStreet, anyPageable);

        assertThat(actualEmptyAddresses).isEmpty();
    }
}
