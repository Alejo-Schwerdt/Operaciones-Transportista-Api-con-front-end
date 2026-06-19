package com.transportista.operaciones.dto.response;

import lombok.Data;

@Data
public class CamioneroResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String numeroLicencia;
    private Boolean habilitado;
}
