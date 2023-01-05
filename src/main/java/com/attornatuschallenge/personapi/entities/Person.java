package com.attornatuschallenge.personapi.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity(name = "Person")
@Table(name = "person")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String birthDate;
    @OneToMany(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Address> addresses = new ArrayList<>();

    public Person(){};

    public Person(Long id, String name, String birthDate) {
        Id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Person(Long id, String name, String birthDate, List<Address> addresses) {
        Id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.addresses = addresses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.setPerson(this);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setPerson(null);
    }

    public Long getId() {
        return Id;
    }
}
