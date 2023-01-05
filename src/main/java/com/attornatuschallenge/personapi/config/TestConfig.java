package com.attornatuschallenge.personapi.config;

import com.attornatuschallenge.personapi.entities.Address;
import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(String ...args) throws Exception {

        Person person1  = new Person(null, "Fulano", "19/02/1996");
        Person person2  = new Person(null, "Cicrano", "13/12/1995");

        Address address1 = new Address(null, "Rua tal", "60730-012", "1432", "Fortaleza", person1);
        Address address2 = new Address(null, "Rua fulana", "61430-112", "765", "Jacarecanga", person2);

        person1.addAddress(address1);
        person2.addAddress(address2);

        personRepository.saveAll(Arrays.asList(person1, person2));
    }

}
