package com.transportista.operaciones.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "viajes")
@Data
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origen;
    private String destino;
    private Double distanciaKm;

    private LocalDate fechaSalida;
    private LocalDate fechaLlegada; // Puede ser null hasta finalizar el viaje

    private Double montoFlete;

    @Enumerated(EnumType.STRING)
    private EstadoViaje estado;

    @ManyToOne
    @JoinColumn(name = "camionero_id")
    private Camionero camionero;

    @ManyToOne
    @JoinColumn(name = "camion_id")
    private Camion camion;
}
