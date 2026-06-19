package com.transportista.operaciones.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.Email;

@Data
public class CamioneroRequestDTO {
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String dni;
    private String telefono;
    @Email
    private String email;
    private String numeroLicencia;
    @NotNull
    private Boolean habilitado;
}
