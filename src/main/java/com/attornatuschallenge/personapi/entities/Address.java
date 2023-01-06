package com.attornatuschallenge.personapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;

@Entity(name = "Address")
@Table(name = "person_address")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty(message = "Logradouro is required.")
    private String logradouro;
    @Pattern(regexp = "^[0-9]{8}$", message = "Invalid CEP")
    private String CEP;
    @NotEmpty(message = "House number is required.")
    private String houseNumber;
    @NotEmpty(message = "City is required.")
    private String city;
    @NotNull(message = "Main Address check is required.")
    private Boolean main;
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    public Address(){}

    public Address(Long id, String logradouro, String CEP, String houseNumber, String city, Boolean main) {
        this.id = id;
        this.logradouro = logradouro;
        this.CEP = CEP;
        this.houseNumber = houseNumber;
        this.city = city;
        this.main = main;
    }

    public Address(Long id, String logradouro, String CEP, String houseNumber, String city, Person person) {
        this.id = id;
        this.logradouro = logradouro;
        this.CEP = CEP;
        this.houseNumber = houseNumber;
        this.city = city;
        this.person = person;
        this.main = false;
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

    @JsonIgnore
    public Long getPersonId() {
        return person.getId();
    }

    @Transient
    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
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
