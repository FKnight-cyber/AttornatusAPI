package com.attornatuschallenge.personapi.repositories;

import com.attornatuschallenge.personapi.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2PersonRepository extends JpaRepository<Person, Integer> {

}
