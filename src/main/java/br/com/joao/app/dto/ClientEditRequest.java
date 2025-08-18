package br.com.joao.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ClientEditRequest(@Size(max = 100) String companyName,
                                @Email String email,
                                @Size(max = 20) String phone,
                                @Valid AddressEditRequest address) {
}
