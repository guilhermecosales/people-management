package com.github.peoplemanagement.utility;

import com.github.peoplemanagement.entity.Address;
import com.github.peoplemanagement.entity.Person;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
public class Factory {

    public static Person createPerson() {
        return new Person(null, "Richard S. Frye", LocalDate.of(1952, 2, 10), null);
    }

    public static Address createAddress() {
        return new Address(null, "Rasmussen Dr Trufant", "49347", "111", "Michigan", true, createPerson());
    }

    public static Address createDefaultAddress(final Person person) {
        return new Address(null, "Rasmussen Dr Trufant", "49347", "111", "Michigan", true, person);
    }

    public static Address createNonDefaultAddress(final Person person) {
        return new Address(null, "Rasmussen Dr Trufant", "49347", "111", "Michigan", false, person);
    }

}
