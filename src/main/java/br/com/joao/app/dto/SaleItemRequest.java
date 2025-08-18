package br.com.joao.app.dto;

import br.com.joao.app.domain.ItemType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaleItemRequest(
        @NotNull ItemType itemType,
        @NotBlank Long itemId,
        @Min(1) int quantity) {
}
