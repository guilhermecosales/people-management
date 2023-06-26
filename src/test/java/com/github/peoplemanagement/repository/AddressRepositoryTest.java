package com.github.peoplemanagement.repository;

import com.github.peoplemanagement.entity.Address;
import com.github.peoplemanagement.entity.Person;
import com.github.peoplemanagement.utility.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    private Person person;

    @BeforeEach
    void setUp() {
        person = Factory.createPerson();
        person = entityManager.persist(person);
        entityManager.flush();
    }

    @Test
    @DisplayName(value = "Return the person's default address")
    void testFindByPersonIdAndDefaultIsTrue_whenPersonHasDefaultAddress_returnDefaultAddress() {
        Address address = Factory.createDefaultAddress(person);
        address = entityManager.persist(address);
        entityManager.flush();

        Optional<Address> defaultAddressFound = addressRepository.findByPersonIdAndDefaultIsTrue(address.getPerson().getPersonId());

        assertTrue(defaultAddressFound.isPresent());
        assertTrue(defaultAddressFound.get().isDefault());
    }

    @Test
    @DisplayName(value = "Return nothing when the person has no default address")
    void testFindByPersonIdAndDefaultIsTrue_whenThePersonDoesNotHaveDefaultAddress_returnEmpty() {
        Address address = Factory.createNonDefaultAddress(person);
        address = entityManager.persist(address);
        entityManager.flush();

        Optional<Address> defaultAddressFound = addressRepository.findByPersonIdAndDefaultIsTrue(address.getPerson().getPersonId());

        assertTrue(defaultAddressFound.isEmpty());
    }
}