package com.yourcity.yourcity.unit.repository;

import com.yourcity.yourcity.TestYourCityApplication;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@Sql({
        "classpath:sql/test-data-clear.sql",
        "classpath:sql/test-data-insert.sql"
})
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = TestYourCityApplication.class)
public class AddressRepositoryTest {

}
