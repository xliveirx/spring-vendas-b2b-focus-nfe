package br.com.joao.app.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductEditRequest(@Positive Integer stockQuantity, @Positive BigDecimal price, String description) {
}
