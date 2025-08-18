package br.com.joao.app.service;

import br.com.joao.app.domain.ServiceEntity;
import br.com.joao.app.domain.exception.ServiceAlreadyRegisteredException;
import br.com.joao.app.domain.exception.ServiceEntityNotFoundException;
import br.com.joao.app.dto.ServiceEditRequest;
import br.com.joao.app.dto.ServiceRequest;
import br.com.joao.app.repository.ServiceEntityRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEntityService {

    private final ServiceEntityRepository serviceEntityRepository;

    public ServiceEntityService(ServiceEntityRepository serviceEntityRepository) {
        this.serviceEntityRepository = serviceEntityRepository;
    }

    @Transactional
    public ServiceEntity registerService(ServiceRequest req) {

        if(serviceEntityRepository.findByNameIgnoreCase(req.name()).isPresent()) {
            throw new ServiceAlreadyRegisteredException();
        }

        return serviceEntityRepository.save(new ServiceEntity(req.name(), req.price(), req.description()));
    }

    @Transactional
    public ServiceEntity updateService(ServiceEditRequest req, Long id) {

        var service = serviceEntityRepository.findById(id)
                .orElseThrow(ServiceEntityNotFoundException::new);

        if(req.price() != null){
            service.setPrice(req.price());
        }

        if(req.description() != null){
            service.setDescription(req.description());
        }

        return serviceEntityRepository.save(service);
    }

    @Transactional
    public void deleteService(Long id) {

        var service = serviceEntityRepository.findById(id)
                .orElseThrow(ServiceEntityNotFoundException::new);

        serviceEntityRepository.delete(service);

    }

    public Page<ServiceEntity> getServices(Pageable pageable) {

        if(serviceEntityRepository.findAll().isEmpty()) {
            throw new ServiceEntityNotFoundException();
        }

        return serviceEntityRepository.findAll(pageable);
    }
}
