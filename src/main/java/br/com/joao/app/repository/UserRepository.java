package br.com.joao.app.repository;

import br.com.joao.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndActiveTrue(String email);
}
