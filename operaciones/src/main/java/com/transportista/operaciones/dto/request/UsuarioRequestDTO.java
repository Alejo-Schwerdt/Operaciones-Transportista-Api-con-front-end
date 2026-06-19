package com.transportista.operaciones.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank
    private String nombre;

    @Email
    private String email;

    @NotBlank
    private String password;
}