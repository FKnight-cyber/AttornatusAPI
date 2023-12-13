package com.attornatuschallenge.personapi.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "Person")
@Table(name = "person")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long Id;
    private Long mainAddressId;
    @NotEmpty(message = "Name is required.")
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

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Long getId() {
        return Id;
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.setPerson(this);
    }

    @JsonIgnore
    public Long getMainAddressId() {
        return mainAddressId;
    }

    public void setMainAddressId(Long mainAddressId) {
        this.mainAddressId = mainAddressId;
    }
}
