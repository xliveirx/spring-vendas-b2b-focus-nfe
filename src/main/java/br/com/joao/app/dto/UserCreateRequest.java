package br.com.joao.app.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(@NotBlank String name, @NotBlank String email, @NotBlank String password, @NotBlank String confirmPassword) {
}
