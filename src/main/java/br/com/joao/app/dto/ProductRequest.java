package br.com.joao.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(@NotBlank String name, @NotBlank @Positive Integer stockQuantity, @NotBlank @Positive BigDecimal price, String description) { }
