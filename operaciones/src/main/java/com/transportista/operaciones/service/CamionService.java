package com.transportista.operaciones.service;

import org.springframework.stereotype.Service;
import com.transportista.operaciones.dto.request.CamionRequestDTO;
import com.transportista.operaciones.dto.response.CamionResponseDTO;
import com.transportista.operaciones.entity.Camion;
import com.transportista.operaciones.entity.EstadoCamion;

import lombok.RequiredArgsConstructor;
import com.transportista.operaciones.mapper.CamionMapper;
import com.transportista.operaciones.repository.CamionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CamionService {
    private final CamionMapper camionMapper;
    private final CamionRepository camionRepository;

    public CamionResponseDTO crearCamion(CamionRequestDTO dto) {
        Camion camion = camionMapper.toEntity(dto);
        camion.setEstado(EstadoCamion.DISPONIBLE);
        camion.setDisponible(true);
        camion = camionRepository.save(camion);
        return camionMapper.toDto(camion);
    }

    public CamionResponseDTO obtenerCamionPorId(Long id) {
        Camion camion = camionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camion no encontrado con ID: " + id));
        return camionMapper.toDto(camion);
    }

    public List<CamionResponseDTO> listarTodosLosCamiones() {
        return camionRepository.findAll().stream()
                .map(camionMapper::toDto)
                .collect(Collectors.toList());
    }

    public CamionResponseDTO actualizarCamion(Long id, CamionRequestDTO dto) {
        Camion camion = camionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado"));

        // Bloqueo de edición si está en viaje
        if (camion.getEstado() == EstadoCamion.EN_VIAJE) {
            throw new IllegalStateException("El camión está actualmente en viaje y no puede ser editado.");
        }

        // Prohibir asignar manualmente el estado EN_VIAJE desde este endpoint
        if (dto.getEstado() == EstadoCamion.EN_VIAJE) {
            throw new IllegalStateException(
                    "No se puede asignar manualmente el estado EN_VIAJE. Este estado lo gestiona el sistema al iniciar un viaje.");
        }

        camion.setPatente(dto.getPatente());
        camion.setMarca(dto.getMarca());
        camion.setModelo(dto.getModelo());
        camion.setAnio(dto.getAnio());
        camion.setCapacidadToneladas(dto.getCapacidadToneladas());
        camion.setEstado(dto.getEstado());

        // 'disponible' es un campo derivado de 'estado', no se recibe del frontend
        camion.setDisponible(dto.getEstado() == EstadoCamion.DISPONIBLE);

        return camionMapper.toDto(camionRepository.save(camion));
    }

    public void eliminarCamion(Long id) {
        if (!camionRepository.existsById(id)) {
            throw new IllegalArgumentException("Camion no encontrado con ID: " + id);
        }
        camionRepository.deleteById(id);
    }

    public Map<String, Long> obtenerEstadisticas() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("disponibles", camionRepository.countByEstado(EstadoCamion.DISPONIBLE));
        stats.put("enViaje", camionRepository.countByEstado(EstadoCamion.EN_VIAJE));
        stats.put("enTaller", camionRepository.countByEstado(EstadoCamion.EN_TALLER));
        return stats;
    }
}
