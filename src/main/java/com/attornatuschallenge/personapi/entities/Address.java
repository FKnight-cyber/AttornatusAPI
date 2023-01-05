package com.attornatuschallenge.personapi.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "Address")
@Table(name = "person_address")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    private String logradouro;
    private String CEP;
    private String houseNumber;
    private String city;
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    public Address(){};

    public Address(Long id, String logradouro, String CEP, String houseNumber, String city) {
        this.id = id;
        this.logradouro = logradouro;
        this.CEP = CEP;
        this.houseNumber = houseNumber;
        this.city = city;
    }

    public Address(Long id, String logradouro, String CEP, String houseNumber, String city, Person person) {
        this.id = id;
        this.logradouro = logradouro;
        this.CEP = CEP;
        this.houseNumber = houseNumber;
        this.city = city;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getPersonId() {
        return person.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address )) return false;
        return id != null && id.equals(((Address) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
