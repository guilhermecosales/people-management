package com.github.peoplemanagement.controller;

import com.github.peoplemanagement.controller.utility.Location;
import com.github.peoplemanagement.dto.request.AddressRequestDto;
import com.github.peoplemanagement.dto.response.AddressDto;
import com.github.peoplemanagement.entity.Address;
import com.github.peoplemanagement.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1")
public class AddressController {

    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "/addresses")
    public ResponseEntity<AddressDto> createPerson(@RequestBody AddressRequestDto request) {
        Address savedAddress = addressService.save(modelMapper.map(request, Address.class));
        final URI location = Location.create(savedAddress.getAddressId());
        return ResponseEntity.created(location).body(modelMapper.map(savedAddress, AddressDto.class));
    }

    @GetMapping(path = "/persons/{personId}/addresses")
    public ResponseEntity<List<AddressDto>> getAddressesByPersonId(@PathVariable(name = "personId") UUID personId) {
        List<Address> savedAddresses = addressService.findByPersonId(personId);
        return ResponseEntity.ok().body(savedAddresses.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList());
    }

}
