package com.attornatuschallenge.personapi.entities;

import java.io.Serializable;

public class AddressResponseData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String logradouro;
    private String cep;
    private String houseNumber;
    private String city;
    private PersonResponseData person;

    public AddressResponseData(){}

    public AddressResponseData(Long id, String logradouro, String cep, String houseNumber, String city, PersonResponseData person) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.houseNumber = houseNumber;
        this.city = city;
        this.person = person;
    }

    public void setPerson(PersonResponseData person) {
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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
}
