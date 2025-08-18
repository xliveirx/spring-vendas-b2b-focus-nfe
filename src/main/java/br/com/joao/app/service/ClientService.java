package br.com.joao.app.service;

import br.com.joao.app.domain.Address;
import br.com.joao.app.domain.Client;
import br.com.joao.app.domain.User;
import br.com.joao.app.domain.exception.ClientNotFoundException;
import br.com.joao.app.domain.exception.CnpjAlreadyRegistered;
import br.com.joao.app.dto.ClientEditRequest;
import br.com.joao.app.dto.ClientRequest;
import br.com.joao.app.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Client registerClient(ClientRequest req, User logged) {

        if(clientRepository.findByCnpj(req.cnpj()).isEmpty() && clientRepository.findByCompanyNameIgnoreCase(req.companyName()).isEmpty()) {
            var client = new Client(
                    req.companyName(),
                    req.cnpj(),
                    req.email(),
                    req.phone(),

                    new Address(
                            req.address().street(),
                            req.address().city(),
                            req.address().neighborhood(),
                            req.address().state(),
                            req.address().zip(),
                            req.address().country()
                    ),
                    logged
            );

            return clientRepository.save(client);
        }

        throw new CnpjAlreadyRegistered();
    }

    @Transactional
    public Client updateClient(ClientEditRequest req, User logged, Long id) {

        var client = clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        if(req.companyName() != null && clientRepository.findByCompanyNameIgnoreCase(req.companyName()).isEmpty()) {
            client.setCompanyName(req.companyName());
        }

        if(req.email() != null) {
            client.setEmail(req.email());
        }

        if(req.phone() != null) {
            client.setPhone(req.phone());
        }

        if (req.address() != null) {
            Address addr = new Address(
                    req.address().street(),
                    req.address().city(),
                    req.address().neighborhood(),
                    req.address().state(),
                    req.address().zip(),
                    req.address().country()
            );
            client.setAddress(addr);
        }

        client.setUpdatedBy(logged);
        client.setUpdatedAt(LocalDateTime.now());

        return clientRepository.save(client);

    }

    @Transactional
    public void deleteClient(Long id, User logged) {

        var client = clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        client.setUpdatedBy(logged);
        client.setUpdatedAt(LocalDateTime.now());
        client.setActive(false);
    }

    public Page<Client> getClients(Pageable pageable) {

        if(clientRepository.findAll().isEmpty()){
            throw new ClientNotFoundException();
        }

        return clientRepository.findAll(pageable);
    }
}
