package com.transportista.operaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.transportista.operaciones.entity.EstadoViaje;
import com.transportista.operaciones.entity.Viaje;
import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    // Busca si existe algún viaje en curso para este camionero
    boolean existsByCamioneroIdAndEstado(Long camioneroId, EstadoViaje estado);

    // Busca si existe algún viaje en curso para este camión
    boolean existsByCamionIdAndEstado(Long camionId, EstadoViaje estado);

    List<Viaje> findByCamioneroId(Long camioneroId);
}
