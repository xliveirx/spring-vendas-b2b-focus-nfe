package br.com.joao.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServiceRequest(@NotBlank String name, @NotNull @Positive BigDecimal price, @NotBlank String description) {
}
