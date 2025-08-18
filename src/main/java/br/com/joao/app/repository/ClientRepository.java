package br.com.joao.app.repository;

import br.com.joao.app.domain.Client;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCnpj(String cnpj);

    Optional<Client> findByCompanyNameIgnoreCase(String companyName);
}
