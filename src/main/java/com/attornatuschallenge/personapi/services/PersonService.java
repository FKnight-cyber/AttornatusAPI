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
    PersonRepository repository;

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findById(Long id) {
        Optional<Person> person = repository.findById(id);
        return person.get();
    };

    public List<Person> findByName(String name) {
        List<Person> people = repository.findByName(name);
        return people;
    };

    public Person insert(Person person) {
        return repository.save(person);
    }

    public Person update(Long id, Person person) {
        Person entity = repository.getReferenceById(id);
        updateData(entity, person);
        return repository.save(entity);
    }

    private void updateData(Person entity, Person person) {
        entity.setName(person.getName());
    }
}
