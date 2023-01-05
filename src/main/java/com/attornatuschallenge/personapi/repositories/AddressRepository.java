package com.attornatuschallenge.personapi.repositories;

import com.attornatuschallenge.personapi.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.main = true")
    Address findCurrentMainAddress();
}
