package com.github.peoplemanagement.service;

import com.github.peoplemanagement.entity.Address;
import com.github.peoplemanagement.repository.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Address save(final Address address) {
        return addressRepository.save(address);
    }

    @Transactional(readOnly = true)
    public List<Address> findByPersonId(final UUID personId) {
        return addressRepository.findByPersonPersonId(personId);
    }

}
