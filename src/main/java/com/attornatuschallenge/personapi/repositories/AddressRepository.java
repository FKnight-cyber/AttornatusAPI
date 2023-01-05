package com.attornatuschallenge.personapi.repositories;

import com.attornatuschallenge.personapi.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
