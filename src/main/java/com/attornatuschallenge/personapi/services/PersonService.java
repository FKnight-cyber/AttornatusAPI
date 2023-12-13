package com.attornatuschallenge.personapi.services;

import com.attornatuschallenge.personapi.entities.Address;
import com.attornatuschallenge.personapi.entities.AddressResponseData;
import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.entities.PersonResponseData;
import com.attornatuschallenge.personapi.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    @Autowired
    private AddressService addressService;

    public List<PersonResponseData> findAll() {

        List<Person> peopleData = repository.findAll();

        List<PersonResponseData> requestData = new ArrayList<>();

        for(Person p : peopleData)
        {
            List<AddressResponseData> filteredAddressData = new ArrayList<>();
            PersonResponseData filteredPersonData = new PersonResponseData(p.getId(),p.getName(),p.getBirthDate(),filteredAddressData);
            for(Address a : p.getAddresses()) {
                AddressResponseData addressInfo = new AddressResponseData(a.getId(), a.getLogradouro(), a.getCEP(), a.getHouseNumber(),
                        a.getCity(), filteredPersonData);

                filteredPersonData.addAddress(addressInfo);
            }
            requestData.add(filteredPersonData);
        }

        return requestData;
    }

    public Person findPersonAllInfoById(Long id) {
        Optional<Person> person = repository.findById(id);
        return person.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no person registered with this id!"));
    };

    public PersonResponseData findById(Long id) {
        Optional<Person> person = repository.findById(id);
        person.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no person registered with this id!"));

        List<AddressResponseData> filteredAddressData = new ArrayList<>();

        PersonResponseData requestData = new PersonResponseData(
                person.get().getId(), person.get().getName(), person.get().getBirthDate(), filteredAddressData);

        populateNewAddressInfo(person.get().getAddresses(), requestData);

        return requestData;
    };

    public List<Person> findByName(String name) {
        // Log a message before executing the database query
        Map<String, Object> logData = new HashMap<>();
        logData.put("query", "findByName");
        logData.put("name", name);
        System.out.println("------------------------------ Logs ----------------------------");
        System.out.println(logData);
        System.out.println("------------------------------ Logs ----------------------------");

        // Execute the database query
        List<Person> people = repository.findByName(name);

        // Log the results of the query
        logData = new HashMap<>();
        logData.put("query", "findByName");
        logData.put("name", name);
        logData.put("result_count", people.size());

        // Return the results of the query
        return people;
    }

    public Person mainAddressInfo(Long id) {
        Person person = findPersonAllInfoById(id);

        if(person.getMainAddressId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This person hasn't registered a main address yet!");
        }

        Address address = addressService.findById(person.getMainAddressId());

        List<Address> mainAddress = new ArrayList<>();

        mainAddress.add(address);

        Person result = new Person(person.getId(), person.getName(), person.getBirthDate(), mainAddress);

        return result;
    }

    public Person insert(Person person) {
        return repository.save(person);
    }

    public Person update(Long id, Person person) {
        try {
            Optional<Person> optionalPerson = repository.findById(id);

            if (optionalPerson.isPresent()) {
                Person entity = optionalPerson.get();
                updateData(entity, person);
                return repository.save(entity);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no person registered with this id!");
            }
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no person registered with this id!");
        }
    }


    private void updateData(Person entity, Person person) {
        entity.setName(person.getName());
    }

    public void populateNewAddressInfo(List<Address> addresses, PersonResponseData person) {
        List<AddressResponseData> filteredAddressData = new ArrayList<>();

        for(Address a : addresses) {
            AddressResponseData addressInfo = new AddressResponseData(a.getId(), a.getLogradouro(), a.getCEP(), a.getHouseNumber(),
                    a.getCity(), person);

            person.addAddress(addressInfo);
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}


