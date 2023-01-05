package com.attornatuschallenge.personapi.controllers;

import com.attornatuschallenge.personapi.entities.Address;
import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.services.AddressService;
import com.attornatuschallenge.personapi.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/people")
public class AddressController {

    @Autowired
    AddressService service;

    @Autowired
    PersonService personService;

    @PutMapping(value = "/address/{id}")
    public ResponseEntity<Address> update(@RequestBody Address address, @PathVariable Long id) {
        Address result = service.update(id, address);
        Person person = personService.findById(result.getPersonId());
        return new ResponseEntity("Updated " + person.getName() +"'s address info", HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/address/add")
    public ResponseEntity<Address> insert(@RequestBody Address address, @PathVariable Long id) {
        Person person = personService.findById(id);
        service.insert(address, id);
        return new ResponseEntity("Added " + person.getName() +"'s new address", HttpStatus.CREATED);
    }
}
