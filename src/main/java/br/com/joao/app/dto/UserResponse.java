package br.com.joao.app.dto;

import br.com.joao.app.domain.User;

public record UserResponse(Long id, String name, String email) {

    public static UserResponse fromDomain(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getUsername());
    }
}
