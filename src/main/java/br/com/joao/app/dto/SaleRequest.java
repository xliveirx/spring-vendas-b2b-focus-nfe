package br.com.joao.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SaleRequest(
        @NotNull @Size(min = 1) @Valid List<SaleItemRequest> items,
        @NotNull Long clientId) {
}
