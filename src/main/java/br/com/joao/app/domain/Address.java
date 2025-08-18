package br.com.joao.app.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String street;
    private String city;
    private String neighborhood;
    private String state;
    private String zip;
    private String country;

    public Address(String street, String city, String neighborhood, String state, String zip, String country) {
        this.street = street;
        this.city = city;
        this.neighborhood = neighborhood;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public Address() {}
}
