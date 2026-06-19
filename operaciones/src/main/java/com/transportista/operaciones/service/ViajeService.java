package com.transportista.operaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.transportista.operaciones.dto.request.ViajeRequestDTO;
import com.transportista.operaciones.dto.response.ViajeResponseDTO;
import com.transportista.operaciones.entity.Camion;
import com.transportista.operaciones.entity.Camionero;
import com.transportista.operaciones.entity.EstadoCamion;
import com.transportista.operaciones.entity.EstadoViaje;
import com.transportista.operaciones.entity.Viaje;
import com.transportista.operaciones.mapper.ViajeMapper;
import com.transportista.operaciones.repository.CamionRepository;
import com.transportista.operaciones.repository.CamioneroRepository;
import com.transportista.operaciones.repository.ViajeRepository;

@Service
@RequiredArgsConstructor
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final ViajeMapper viajeMapper;
    private final CamioneroRepository camioneroRepository;
    private final CamionRepository camionRepository;

    // Crear viaje
    public ViajeResponseDTO crearViaje(ViajeRequestDTO request) {
        // 1. Validar disponibilidad del Camionero
        boolean camioneroOcupado = viajeRepository.existsByCamioneroIdAndEstado(request.getCamioneroId(),
                EstadoViaje.EN_CURSO);
        if (camioneroOcupado) {
            throw new IllegalStateException("El camionero ya tiene un viaje en curso.");
        }

        // 2. Validar disponibilidad del Camión
        boolean camionOcupado = viajeRepository.existsByCamionIdAndEstado(request.getCamionId(), EstadoViaje.EN_CURSO);
        if (camionOcupado) {
            throw new IllegalStateException("El camión ya está siendo utilizado en otro viaje.");
        }

        Camionero camionero = camioneroRepository.findById(request.getCamioneroId())
                .orElseThrow(() -> new IllegalArgumentException("Camionero no encontrado"));

        Camion camion = camionRepository.findById(request.getCamionId())
                .orElseThrow(() -> new IllegalArgumentException("Camion no encontrado"));

        // Opcional: Si el camión está en taller, tampoco dejar crear el viaje
        if (camion.getEstado() == EstadoCamion.EN_TALLER) {
            throw new IllegalStateException("El camión está en mantenimiento (Taller).");
        }

        Viaje viaje = viajeMapper.toEntity(request);
        viaje.setCamionero(camionero);
        viaje.setCamion(camion);
        viaje.setEstado(EstadoViaje.PENDIENTE);

        // Si al crear ya le pones EN_CURSO, también deberías marcar el camión como
        // EN_VIAJE aquí
        if (request.getEstado() == EstadoViaje.EN_CURSO) {
            camion.setEstado(EstadoCamion.EN_VIAJE);
            camionRepository.save(camion);
        }

        return viajeMapper.toDto(viajeRepository.save(viaje));
    }

    // Obtener por ID
    public ViajeResponseDTO obtenerViajePorId(Long id) {
        Viaje viaje = viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado con ID: " + id));

        return viajeMapper.toDto(viaje);
    }

    // Obtener viajes por camionero
    public List<ViajeResponseDTO> obtenerViajesPorCamioneroId(Long camioneroId) {
        List<Viaje> viajes = viajeRepository.findByCamioneroId(camioneroId);
        return viajes.stream()
                .map(viajeMapper::toDto)
                .toList();
    }

    // Listar todos
    public List<ViajeResponseDTO> listarViajes() {
        List<Viaje> viajes = viajeRepository.findAll();
        return viajes.stream()
                .map(viajeMapper::toDto)
                .toList();
    }

    // Actualizar viaje
    @Transactional
    public ViajeResponseDTO actualizarViaje(Long id, ViajeRequestDTO request) {
        Viaje viaje = viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        // 1. Regla: Prohibir edición si ya terminó
        if (viaje.getEstado() == EstadoViaje.FINALIZADO) {
            throw new IllegalStateException("No se puede editar un viaje que ya ha sido finalizado.");
        }

        EstadoViaje estadoAnterior = viaje.getEstado();

        // 2. Actualización de datos
        viaje.setOrigen(request.getOrigen());
        viaje.setDestino(request.getDestino());
        viaje.setDistanciaKm(request.getDistanciaKm());
        viaje.setFechaSalida(request.getFechaSalida());
        viaje.setMontoFlete(request.getMontoFlete());
        viaje.setEstado(request.getEstado());

        // Manejo de fecha llegada automática
        if (request.getEstado() == EstadoViaje.FINALIZADO && viaje.getFechaLlegada() == null) {
            viaje.setFechaLlegada(LocalDate.now());
        }

        // 3. Sincronización con Camión (Aquí debe ir dentro del método)
        if (estadoAnterior != request.getEstado() && viaje.getCamion() != null) {
            Camion camion = viaje.getCamion();

            if (request.getEstado() == EstadoViaje.EN_CURSO) {
                camion.setEstado(EstadoCamion.EN_VIAJE);
            } else if (request.getEstado() == EstadoViaje.FINALIZADO ||
                    request.getEstado() == EstadoViaje.CANCELADO) {
                camion.setEstado(EstadoCamion.DISPONIBLE);
            }
            camionRepository.save(camion);
        }

        return viajeMapper.toDto(viajeRepository.save(viaje));
    }

    // Eliminar viaje
    public void eliminarViaje(Long id) {
        Viaje viaje = viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado con ID: " + id));

        // Regla: No eliminar viajes en curso
        if (viaje.getEstado() == EstadoViaje.EN_CURSO) {
            throw new IllegalStateException("No se puede eliminar un viaje que está en curso.");
        }

        viajeRepository.deleteById(id);
    }
}