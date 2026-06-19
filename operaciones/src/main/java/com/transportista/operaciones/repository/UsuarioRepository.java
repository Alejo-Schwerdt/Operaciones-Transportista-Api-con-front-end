package com.transportista.operaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transportista.operaciones.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
