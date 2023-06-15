package com.github.peoplemanagement.repository;

import com.github.peoplemanagement.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findByPersonPersonId(UUID personId);

}
