package com.transportista.operaciones.dto.request;

import java.time.LocalDate;

import com.transportista.operaciones.entity.EstadoViaje;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Data
public class ViajeRequestDTO {
    @NotBlank
    private String origen;
    @NotBlank
    private String destino;
    @NotNull
    private Double distanciaKm;

    private LocalDate  fechaSalida;
    @NotNull
    private Double montoFlete;   
    private EstadoViaje estado;
    private Long camioneroId;
    private Long camionId;
}
