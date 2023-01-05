package com.attornatuschallenge.personapi.services;

import com.attornatuschallenge.personapi.entities.Address;
import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.repositories.AddressRepository;
import com.attornatuschallenge.personapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository repository;

    @Autowired
    PersonRepository personRepository;

    public Address findById(Long id) {
        Optional<Address> address = repository.findById(id);
        return address.get();
    };

    public Address insert(Address address, Long id) {
        Optional<Person> person = personRepository.findById(id);

        if(address.getMain()) {
            Address checkCurrentMain = repository.findCurrentMainAddress();
            if(checkCurrentMain != null) {
                checkCurrentMain.setMain(false);
            }
        }

        address.setPerson(person.get());
        person.get().addAddress(address);

        return repository.save(address);
    }

    public Address update(Long id, Address address) {
        Address entity = repository.getReferenceById(id);
        updateData(entity, address);
        return repository.save(entity);
    }

    private void updateData(Address entity, Address address) {
        entity.setLogradouro(address.getLogradouro());
        entity.setCEP(address.getCEP());
        entity.setHouseNumber(address.getHouseNumber());
        entity.setCity(address.getCity());
        entity.setMain(address.getMain());
    }
}
