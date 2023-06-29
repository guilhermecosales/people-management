package com.github.peoplemanagement.service;

import com.github.peoplemanagement.entity.Person;
import com.github.peoplemanagement.service.exception.NotFoundException;
import com.github.peoplemanagement.utility.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

@SpringBootTest
class PersonServiceIntegrationTest {

    @Autowired
    private PersonService personService;

    private Person person;

    @BeforeEach
    void setUp() {
        person = Factory.createPerson();
    }

    @Test
    void testSave_whenDataProvidedIsValid_thenReturnSavedPerson() {
        Person savedPerson = personService.save(person);

        Assertions.assertEquals(person.getName(), savedPerson.getName());
        Assertions.assertEquals(person.getBirthDate(), savedPerson.getBirthDate());
    }

    @Test
    void testSave_whenNameProvidedIsNull_thenThrowException() {
        person.setName(null);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> personService.save(person));
    }

    @Test
    void testSave_whenBirthDateProvidedIsNull_thenThrowException() {
        person.setBirthDate(null);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> personService.save(person));
    }

    @Test
    void testFindAll_whenThereArePeopleRegistered_thenReturnPeoplePageList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        personService.save(person);

        Page<Person> peopleSaved = personService.findAll(pageRequest);

        Assertions.assertFalse(peopleSaved.isEmpty());
        Assertions.assertEquals(1, peopleSaved.getTotalElements());
        Assertions.assertEquals(1, peopleSaved.getTotalPages());
    }

    @Test
    void testFindById_whenPersonIdProvidedExists_thenReturnPersonData() {
        person = personService.save(person);

        Person personFound = personService.findById(person.getPersonId());

        Assertions.assertEquals(person.getPersonId(), personFound.getPersonId());
        Assertions.assertEquals(person.getName(), personFound.getName());
        Assertions.assertEquals(person.getBirthDate(), personFound.getBirthDate());
    }

    @Test
    void testFindById_whenPersonIdProvidedDoesNotExist_thenThrowException() {
        Assertions.assertThrowsExactly(NotFoundException.class, () -> personService.findById(UUID.randomUUID()));
    }

}