package br.com.joao.app.dto;

import br.com.joao.app.domain.Client;
import br.com.joao.app.domain.Sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponse(Long id, ClientResponse client, List<SaleItemResponse> items, BigDecimal total, LocalDateTime createdAt) {

    public static SaleResponse fromDomain(Sale sale) {
        return new SaleResponse(
                sale.getId(),
                ClientResponse.fromDomain(sale.getClient()),
                sale.getItems().stream().map(SaleItemResponse::fromDomain).toList(),
                sale.getTotal(),
                sale.getCreatedAt()
        );
    }
}
