package com.attornatuschallenge.personapi.controllers;

import com.attornatuschallenge.personapi.entities.Address;
import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.services.AddressService;
import com.attornatuschallenge.personapi.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/people")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping
    public ResponseEntity<List<Person>> getRegisteredPeople() {

        List<Person> list = service.findAll();

        return ResponseEntity.ok().body(list);
    };

    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        Person person = service.findById(id);
        return ResponseEntity.ok().body(person);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person person) {
        person = service.update(id, person);
        return new ResponseEntity(person.getName() + "'s data successfully updated!", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/address")
    public ResponseEntity<List<Address>> getPersonAddresses(@PathVariable Long id) {
        Person person = service.findById(id);
        return ResponseEntity.ok().body(person.getAddresses());
    }

    @GetMapping(value = "/{id}/address/main")
    public ResponseEntity<Person> getPersonMainAddress(@PathVariable Long id) {
        Person response = service.mainAddressInfo(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/name")
    public ResponseEntity<List<Person>> findByName(@RequestParam String name) {
       return new ResponseEntity<List<Person>>(service.findByName(name), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Person> insert(@RequestBody Person person) {
        service.insert(person);
        return new ResponseEntity(person.getName() + " successfully registered!", HttpStatus.CREATED);
    }
}
