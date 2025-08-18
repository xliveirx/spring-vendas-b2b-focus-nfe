package br.com.joao.app.controller;

import br.com.joao.app.dto.ServiceEditRequest;
import br.com.joao.app.dto.ServiceRequest;
import br.com.joao.app.dto.ServiceResponse;
import br.com.joao.app.service.ServiceEntityService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/services")
public class ServiceEntityController {

    private final ServiceEntityService serviceEntityService;

    public ServiceEntityController(ServiceEntityService serviceEntityService) {
        this.serviceEntityService = serviceEntityService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> registerService (@RequestBody @Valid ServiceRequest req) {

        var service = serviceEntityService.registerService(req);

        var res = ServiceResponse.fromDomain(service);

        return ResponseEntity.created(URI.create("/services/" + service.getId())).body(res);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable Long id,
                                                         @RequestBody(required = false) @Valid ServiceEditRequest req) {

        var service = serviceEntityService.updateService(req, id);

        var res = ServiceResponse.fromDomain(service);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {

        serviceEntityService.deleteService(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<Page<ServiceResponse>> getServices(@RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, size);

        var services = serviceEntityService.getServices(pageable);

        var res = services
                .map(ServiceResponse::fromDomain);

        return ResponseEntity.ok(res);
    }
}
