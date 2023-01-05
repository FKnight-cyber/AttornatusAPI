package com.attornatuschallenge.personapi.services;

import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.get();
    };

    public List<Person> findByName(String name) {
        List<Person> people = personRepository.findByName(name);
        return people;
    };

    public Person insert(Person person) {
        return personRepository.save(person);
    }
}
