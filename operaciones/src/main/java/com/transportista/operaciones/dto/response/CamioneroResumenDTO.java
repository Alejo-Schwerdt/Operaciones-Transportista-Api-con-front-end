package com.transportista.operaciones.dto.response;

import lombok.Data;

@Data
public class CamioneroResumenDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private Boolean habilitado;

}
