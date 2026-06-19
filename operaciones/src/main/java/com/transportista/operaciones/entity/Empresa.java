package com.transportista.operaciones.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "empresas")
@Data
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String cuit;
    private String direccion;
    private String telefono;
    private String email;
    private Double porcentajeRetencion;
    private Boolean activa;
    @ManyToMany
    @JoinTable(
        name = "empresa_camionero",
        joinColumns = @JoinColumn(name = "empresa_id"),
        inverseJoinColumns = @JoinColumn(name = "camionero_id")
    )
    private List<Camionero> camioneros;
}