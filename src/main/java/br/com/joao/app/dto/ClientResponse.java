package br.com.joao.app.dto;

import br.com.joao.app.domain.Address;
import br.com.joao.app.domain.Client;

public record ClientResponse(Long id, String companyName, String cnpj, String email, String phone, AddressResponse address) {

    public static ClientResponse fromDomain(Client client) {
        return new ClientResponse(client.getId(), client.getCompanyName(), client.getCnpj(), client.getEmail(), client.getPhone(), AddressResponse.fromDomain(client.getAddress()));
    }
}
