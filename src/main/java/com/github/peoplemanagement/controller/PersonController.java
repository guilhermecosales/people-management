package com.github.peoplemanagement.controller;

import com.github.peoplemanagement.controller.utility.Location;
import com.github.peoplemanagement.dto.request.PersonRequestDto;
import com.github.peoplemanagement.dto.response.PersonDto;
import com.github.peoplemanagement.entity.Person;
import com.github.peoplemanagement.service.PersonService;
import jakarta.validation.Valid;
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
    public ResponseEntity<PersonDto> createPerson(@RequestBody @Valid PersonRequestDto request) {
        Person savedPerson = personService.save(modelMapper.map(request, Person.class));
        final URI location = Location.create(savedPerson.getPersonId());
        return ResponseEntity.created(location).body(modelMapper.map(savedPerson, PersonDto.class));
    }

    @PutMapping(path = "/{personId}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable(name = "personId") UUID personId, @RequestBody @Valid PersonRequestDto request) {
        Person newPersonData = modelMapper.map(request, Person.class);
        Person updatedPerson = personService.update(personId, newPersonData);
        return ResponseEntity.ok().body(modelMapper.map(updatedPerson, PersonDto.class));
    }

    @GetMapping(path = "/{personId}")
    public ResponseEntity<PersonDto> getOnePerson(@PathVariable(name = "personId") UUID personId) {
        return ResponseEntity.ok().body(modelMapper.map(personService.findById(personId), PersonDto.class));
    }

    @GetMapping
    public ResponseEntity<Page<PersonDto>> getPersons(Pageable pageable) {
        Page<Person> peopleSaved = personService.findAll(pageable);
        return ResponseEntity.ok().body(peopleSaved.map(person -> modelMapper.map(person, PersonDto.class)));
    }

}
