package com.transportista.operaciones.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "camiones")
@Data
public class Camion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private Double capacidadToneladas;
    private Boolean disponible;
    @Enumerated(EnumType.STRING)
    private EstadoCamion estado = EstadoCamion.DISPONIBLE;

}
