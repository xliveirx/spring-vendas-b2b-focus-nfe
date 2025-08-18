package br.com.joao.app.controller;

import br.com.joao.app.domain.User;
import br.com.joao.app.dto.ClientEditRequest;
import br.com.joao.app.dto.ClientRequest;
import br.com.joao.app.dto.ClientResponse;
import br.com.joao.app.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> registerClient(@RequestBody @Valid ClientRequest req,
                                                         @AuthenticationPrincipal User logged){

        var client = clientService.registerClient(req, logged);

        var res = ClientResponse.fromDomain(client);

        return ResponseEntity.created(URI.create("/clients/" + client.getId())).body(res);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateClient(@RequestBody(required = false) @Valid ClientEditRequest req,
                                                       @AuthenticationPrincipal User logged,
                                                       @PathVariable Long id){

        var client = clientService.updateClient(req, logged, id);

        var res = ClientResponse.fromDomain(client);

        return ResponseEntity.ok().body(res);

    }

    @DeleteMapping("/disable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id, @AuthenticationPrincipal User logged){

        clientService.deleteClient(id, logged);

        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/enable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> enableClient(@PathVariable Long id,
                                             @AuthenticationPrincipal User logged){

        clientService.enableClient(id, logged);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<Page<ClientResponse>> getClients(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        var clients = clientService.getClients(pageable);

        var res = clients.map(ClientResponse::fromDomain);

        return ResponseEntity.ok().body(res);

    }
}
