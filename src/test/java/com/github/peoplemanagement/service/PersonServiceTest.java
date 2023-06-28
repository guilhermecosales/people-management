package com.github.peoplemanagement.service;

import com.github.peoplemanagement.entity.Person;
import com.github.peoplemanagement.repository.PersonRepository;
import com.github.peoplemanagement.service.exception.NotFoundException;
import com.github.peoplemanagement.utility.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    private Person person;
    private Person returnPerson;

    @BeforeEach
    void setUp() {
        person = Factory.createPerson();
        returnPerson = Factory.createPerson(UUID.randomUUID());
    }

    @Test
    void testSave_whenDataProvidedIsValid_thenReturnSavedPerson() {
        Mockito.when(personRepository.save(Mockito.any())).thenReturn(returnPerson);

        Person savedPerson = personService.save(person);

        Assertions.assertEquals(person.getName(), savedPerson.getName());
        Assertions.assertEquals(person.getBirthDate(), savedPerson.getBirthDate());
    }

    @Test
    void testSave_whenNameProvidedIsNull_thenThrowException() {
        person.setName(null);
        Mockito.doThrow(DataIntegrityViolationException.class).when(personRepository).save(person);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> personService.save(person));
    }

    @Test
    void testSave_whenBirthDateProvidedIsNull_thenThrowException() {
        person.setBirthDate(null);
        Mockito.doThrow(DataIntegrityViolationException.class).when(personRepository).save(person);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> personService.save(person));
    }

    @Test
    void testUpdate_whenPersonDoesNotExistAndValidDataIsProvided_thenThrowException() {
        Mockito.doThrow(EntityNotFoundException.class).when(personRepository).getReferenceById(Mockito.any(UUID.class));

        Assertions.assertThrowsExactly(NotFoundException.class, () -> personService.update(returnPerson.getPersonId(), person));
    }

    @Test
    void testFindById_whenIDProvidedExists_thenReturnSavedPerson() {
        Mockito.when(personRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(returnPerson));

        Assertions.assertDoesNotThrow(() -> personService.findById(UUID.randomUUID()));
    }

    @Test
    void testFindById_whenIDProvidedDoesNotExist_thenThrowException() {
        Mockito.when(personRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(NotFoundException.class, () -> personService.findById(UUID.randomUUID()));
    }

    @Test
    void testFindAll_whenThereArePeopleRegistered_thenReturnPaginatedPeopleList() {
        Page<Person> pageToReturn = new PageImpl<>(List.of(returnPerson));
        Mockito.when(personRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageToReturn);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Person> result = personService.findAll(pageable);

        Assertions.assertNotNull(result);
    }

}