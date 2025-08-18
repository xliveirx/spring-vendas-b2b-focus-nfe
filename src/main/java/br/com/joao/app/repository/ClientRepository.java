package br.com.joao.app.repository;

import br.com.joao.app.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCnpjAndActiveTrue(String cnpj);

    Optional<Client> findByCompanyNameIgnoreCaseAndActiveTrue(String companyName);

    Page<Client> findAllByActiveTrue(Pageable pageable);

    Optional<Client> findByIdAndActiveTrue(Long id);

    Optional<Client> findByIdAndActiveFalse(Long id);
}
