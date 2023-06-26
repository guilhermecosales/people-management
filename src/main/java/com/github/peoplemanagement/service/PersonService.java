package com.github.peoplemanagement.service;

import com.github.peoplemanagement.entity.Person;
import com.github.peoplemanagement.repository.PersonRepository;
import com.github.peoplemanagement.service.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Person save(final Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public Person update(final UUID personId, final Person newPersonData) {
        try {
            final Person savedPerson = personRepository.getReferenceById(personId);

            TypeMap<Person, Person> personMapper = modelMapper.createTypeMap(Person.class, Person.class);
            personMapper.addMappings(mapper -> mapper.skip(Person::setPersonId));
            modelMapper.map(newPersonData, savedPerson);

            return personRepository.save(savedPerson);
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("Person with ID " + personId + " not found.");
        }
    }

    @Transactional(readOnly = true)
    public Person findById(final UUID personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new NotFoundException("Person with ID " + personId + " not found."));
    }

    @Transactional(readOnly = true)
    public Page<Person> findAll(final Pageable pageable) {
        return personRepository.findAll(pageable);
    }

}
