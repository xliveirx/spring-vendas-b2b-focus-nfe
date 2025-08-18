package br.com.joao.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientRequest(@NotBlank @Size(max = 100) String companyName,
                            @NotBlank @Pattern(regexp = "\\d{14}") String cnpj,
                            @NotBlank @Email String email,
                            @NotBlank @Size(max = 20) String phone,
                            @Valid AddressRequest address
                            ) {
}
