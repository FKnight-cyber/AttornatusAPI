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
        Person person3  = new Person(null, "Fulano de Tal", "20/02/1994");

        Address address1 = new Address(null, "Rua tal", "60730012", "1432", "Fortaleza", person1);
        Address address2 = new Address(null, "Rua fulana", "61430112", "765", "Jacarecanga", person2);
        Address address3 = new Address(null, "Rua distante de tudo", "61535111", "567", "Nowhere", person3);

        person1.addAddress(address1);
        person2.addAddress(address2);
        person3.addAddress(address3);

        person3.setMainAddressId(Long.valueOf(3));
        address3.setMain(true);

        personRepository.saveAll(Arrays.asList(person1, person2, person3));
    }

}
