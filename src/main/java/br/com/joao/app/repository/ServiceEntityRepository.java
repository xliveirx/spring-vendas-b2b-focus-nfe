package br.com.joao.app.repository;

import br.com.joao.app.domain.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
    Optional<ServiceEntity> findByNameIgnoreCase(String name);
}
