package com.transportista.operaciones.dto.request;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Data
public class EmpresaRequestDTO {
    @NotBlank
    private String nombre;
    @NotBlank
    private String cuit;
    private String direccion;
    private String telefono;
    @Email
    private String email;
    @NotNull    
    private Double porcentajeRetencion;
    private Boolean activa;

}
