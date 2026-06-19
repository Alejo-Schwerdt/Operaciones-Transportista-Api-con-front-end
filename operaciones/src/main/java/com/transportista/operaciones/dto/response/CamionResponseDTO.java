package com.transportista.operaciones.dto.response;

import com.transportista.operaciones.entity.EstadoCamion;

import lombok.Data;

@Data
public class CamionResponseDTO {
    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private Double capacidadToneladas;
    private Boolean disponible;
    private EstadoCamion estado;
}
