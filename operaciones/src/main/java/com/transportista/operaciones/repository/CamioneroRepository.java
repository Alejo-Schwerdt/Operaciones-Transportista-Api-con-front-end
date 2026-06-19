package com.transportista.operaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.transportista.operaciones.entity.Camionero;
import java.util.Optional;
import java.util.List;

public interface CamioneroRepository extends JpaRepository<Camionero, Long> {
    Optional<Camionero> findByEmail(String email);

    Optional<Camionero> findByDni(String dni);

    Boolean existsByDni(String dni);

    List<Camionero> findByHabilitadoTrue();
}
