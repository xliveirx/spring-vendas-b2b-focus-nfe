package br.com.joao.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequest(@NotBlank
                             @Size(max = 100)
                             String street,

                             @NotBlank
                             @Size(max = 50)
                             String city,

                             @Size(max = 50)
                             String neighborhood,

                             @NotBlank
                             @Size(min = 2, max = 2)
                             String state,

                             @NotBlank
                             @Pattern(regexp = "\\d{5}-\\d{3}")
                             String zip,

                             @Size(max = 50)
                             String country) {
}
