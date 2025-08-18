package br.com.joao.app.dto;

import br.com.joao.app.domain.ServiceEntity;

import java.math.BigDecimal;

public record ServiceResponse(Long id, String name, BigDecimal price, String description) {

    public static ServiceResponse fromDomain(ServiceEntity service) {
        return new ServiceResponse(service.getId(), service.getName(), service.getPrice(), service.getDescription());
    }
}
