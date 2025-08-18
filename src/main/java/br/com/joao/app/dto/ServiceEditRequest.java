package br.com.joao.app.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServiceEditRequest(@Positive BigDecimal price, String description) {
}
