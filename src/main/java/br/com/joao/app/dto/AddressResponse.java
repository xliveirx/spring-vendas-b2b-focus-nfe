package br.com.joao.app.dto;

import br.com.joao.app.domain.Address;

public record AddressResponse(String street, String city, String neighborhood, String state, String zip, String country){

    public static AddressResponse fromDomain(Address address) {
        return new AddressResponse(address.getStreet(), address.getCity(), address.getNeighborhood(), address.getState(), address.getZip(), address.getCountry());
    }
}
