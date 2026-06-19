package com.transportista.operaciones.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaResponseDTO {
    private Long id;
    private String nombre;
    private String cuit;
    private String direccion;
    private String telefono;
    private String email;
    private Double porcentajeRetencion;
    private Boolean activa;
    private List<CamioneroResumenDTO> camioneros;
}
