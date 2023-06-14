package com.github.peoplemanagement.controller;

import com.github.peoplemanagement.controller.utility.Location;
import com.github.peoplemanagement.dto.request.PersonRequest;
import com.github.peoplemanagement.dto.response.PersonResponse;
import com.github.peoplemanagement.entity.Person;
import com.github.peoplemanagement.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/persons")
public class PersonController {

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@RequestBody PersonRequest request) {
        Person savedPerson = personService.save(modelMapper.map(request, Person.class));
        final URI location = Location.create(savedPerson.getPersonId());
        return ResponseEntity.created(location).body(modelMapper.map(savedPerson, PersonResponse.class));
    }

    @PutMapping(path = "/{personId}")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable(name = "personId") UUID personId, @RequestBody PersonRequest request) {
        Person newPersonData = modelMapper.map(request, Person.class);
        Person updatedPerson = personService.update(personId, newPersonData);
        return ResponseEntity.ok().body(modelMapper.map(updatedPerson, PersonResponse.class));
    }

    @GetMapping(path = "/{personId}")
    public ResponseEntity<PersonResponse> getOnePerson(@PathVariable(name = "personId") UUID personId) {
        return ResponseEntity.ok().body(modelMapper.map(personService.findById(personId), PersonResponse.class));
    }

    @GetMapping
    public ResponseEntity<Page<PersonResponse>> getPersons(Pageable pageable) {
        Page<Person> peopleSaved = personService.findAll(pageable);
        return ResponseEntity.ok().body(peopleSaved.map(person -> modelMapper.map(person, PersonResponse.class)));
    }

}
