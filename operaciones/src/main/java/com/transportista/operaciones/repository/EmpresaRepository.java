package com.transportista.operaciones.repository;

import com.transportista.operaciones.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByCuit(String cuit);

    Optional<Empresa> findByNombre(String nombre);

    boolean existsByCuit(String cuit);
}
