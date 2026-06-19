package com.transportista.operaciones.repository;

import com.transportista.operaciones.entity.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import com.transportista.operaciones.entity.EstadoCamion;

public interface CamionRepository extends JpaRepository<Camion, Long> {
    long countByEstado(EstadoCamion estado);
}
