package com.attornatuschallenge.personapi.repositories;

import com.attornatuschallenge.personapi.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
