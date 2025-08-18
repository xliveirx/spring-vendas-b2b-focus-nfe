package br.com.joao.app.dto;

import br.com.joao.app.domain.Product;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, Integer stockQuantity, BigDecimal price, String description) {
    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getStockQuantity(), product.getPrice(), product.getDescription());
    }
}
