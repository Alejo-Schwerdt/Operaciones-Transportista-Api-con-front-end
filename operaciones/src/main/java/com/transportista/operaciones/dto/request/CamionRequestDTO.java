package com.transportista.operaciones.dto.request;

import com.transportista.operaciones.entity.EstadoCamion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CamionRequestDTO {
    @NotBlank
    private String patente;
    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    private Integer anio;
    @NotNull
    private Double capacidadToneladas;
    @NotNull
    private Boolean disponible;
    @NotNull
    private EstadoCamion Estado;
}
