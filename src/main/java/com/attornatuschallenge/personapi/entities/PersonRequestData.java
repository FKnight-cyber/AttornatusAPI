package com.attornatuschallenge.personapi.entities;

public class PersonRequestData {
    private String name;
    private String birthDate;

    public PersonRequestData() {}

    public PersonRequestData(String name) {
        this.name = name;
    }

    public PersonRequestData(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
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
}
