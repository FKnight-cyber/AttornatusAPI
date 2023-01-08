package com.attornatuschallenge.personapi.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonResponseData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long Id;
    private String name;
    private String birthDate;
    private List<AddressResponseData> addresses = new ArrayList<>();

    public PersonResponseData(){}

    public PersonResponseData(Long id, String name, String birthDate, List<AddressResponseData> addresses) {
        this.Id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.addresses = addresses;
    }

    public void addAddress(AddressResponseData address) {
        addresses.add(address);
        address.setPerson(this);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public List<AddressResponseData> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponseData> addresses) {
        this.addresses = addresses;
    }
}
