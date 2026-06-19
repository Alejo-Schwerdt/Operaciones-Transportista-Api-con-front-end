package com.transportista.operaciones.dto.response;

import java.time.LocalDate;
import com.transportista.operaciones.entity.EstadoViaje;
import lombok.Data;

@Data
public class ViajeResponseDTO {
    private Long id;
    private String origen;
    private String destino;
    private Double distanciaKm;
    private LocalDate fechaSalida;
    private LocalDate fechaLlegada;
    private Double montoFlete;
    private EstadoViaje estado;

    private Long camioneroId;
    private String nombreCamionero;

    private Long camionId;
    private String patenteCamion;
}
