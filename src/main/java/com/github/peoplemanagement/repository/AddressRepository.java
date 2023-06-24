package com.github.peoplemanagement.repository;

import com.github.peoplemanagement.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findByPersonPersonId(UUID personId);

    @Query("select a from Address a where a.person.personId = ?1 and a.isDefault = true")
    Optional<Address> findByPersonIdAndDefaultIsTrue(UUID personId);

}
