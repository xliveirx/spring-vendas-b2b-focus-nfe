package br.com.joao.app.controller;

import br.com.joao.app.dto.ServiceEditRequest;
import br.com.joao.app.dto.ServiceRequest;
import br.com.joao.app.dto.ServiceResponse;
import br.com.joao.app.service.ServiceEntityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ServiceResponse> registerService (@RequestBody @Valid ServiceRequest req) {

        var service = serviceEntityService.registerService(req);

        var res = ServiceResponse.fromDomain(service);

        return ResponseEntity.created(URI.create("/services/" + service.getId())).body(res);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable Long id,
                                                         @RequestBody @Valid ServiceEditRequest req) {

        var service = serviceEntityService.updateService(req, id);

        var res = ServiceResponse.fromDomain(service);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {

        serviceEntityService.deleteService(id);

        return ResponseEntity.noContent().build();
    }


}
