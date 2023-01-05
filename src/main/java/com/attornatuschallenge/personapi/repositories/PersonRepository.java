package com.attornatuschallenge.personapi.repositories;

import com.attornatuschallenge.personapi.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p FROM Person p WHERE p.name LIKE :name%")
    List<Person> findByName(@Param("name") String name);
}
