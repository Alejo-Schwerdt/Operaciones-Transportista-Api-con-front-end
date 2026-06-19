package com.transportista.operaciones.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "camioneros")
@Data
public class Camionero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String numeroLicencia;
    private Boolean habilitado;

    @JsonIgnore
    @ManyToMany(mappedBy = "camioneros")
    private List<Empresa> empresas = new ArrayList<>();
}