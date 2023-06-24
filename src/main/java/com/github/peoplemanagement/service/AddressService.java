package com.github.peoplemanagement.service;

import com.github.peoplemanagement.entity.Address;
import com.github.peoplemanagement.repository.AddressRepository;
import com.github.peoplemanagement.repository.PersonRepository;
import com.github.peoplemanagement.service.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Address save(final Address newAddress) {
        validateIfPersonExists(newAddress.getPerson().getPersonId());
        changeCurrentAddressIfNewIsDefault(newAddress.getPerson().getPersonId(), newAddress);
        return addressRepository.save(newAddress);
    }

    @Transactional
    public Address partiallyUpdate(final UUID addressId, final Address addressUpdate) {
        try {
            final Address savedAddress = addressRepository.getReferenceById(addressId);

            changeCurrentAddressIfNewIsDefault(savedAddress.getPerson().getPersonId(), addressUpdate);

            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(addressUpdate, savedAddress);

            return addressRepository.save(savedAddress);
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("Address with ID " + addressId + " not found.");
        }
    }

    @Transactional(readOnly = true)
    public List<Address> findByPersonId(final UUID personId) {
        return addressRepository.findByPersonPersonId(personId);
    }

    private void validateIfPersonExists(final UUID personId) {
        if (!personRepository.existsById(personId)) {
            throw new NotFoundException("Person with ID " + personId + " not found.");
        }
    }

    private void changeCurrentAddressIfNewIsDefault(final UUID personId, final Address newAddress) {
        if (newAddress.isDefault()) {
            final Optional<Address> currentDefaultAddress = addressRepository.findByPersonIdAndDefaultIsTrue(personId);
            currentDefaultAddress.ifPresent(this::changeAddressToNonDefault);
        }
    }

    private void changeAddressToNonDefault(final Address currentDefaultAddress) {
        currentDefaultAddress.setDefault(false);
        addressRepository.save(currentDefaultAddress);
    }

}
