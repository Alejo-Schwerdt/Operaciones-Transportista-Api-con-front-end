package com.transportista.operaciones.service;

import com.transportista.operaciones.dto.request.CamioneroRequestDTO;
import com.transportista.operaciones.dto.response.CamioneroResponseDTO;
import com.transportista.operaciones.entity.Camionero;
import com.transportista.operaciones.entity.Empresa;
import com.transportista.operaciones.mapper.CamioneroMapper;
import com.transportista.operaciones.repository.EmpresaRepository;
import com.transportista.operaciones.repository.CamioneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CamioneroService {

    private final CamioneroRepository camioneroRepository;
    private final CamioneroMapper camioneroMapper;
    private final EmpresaRepository empresaRepository;

    public CamioneroResponseDTO crearCamionero(CamioneroRequestDTO request) {
        if (camioneroRepository.existsByDni(request.getDni())) {
            throw new IllegalArgumentException("El camionero con DNI " + request.getDni() + " ya existe.");
        }
        Camionero camionero = camioneroMapper.toEntity(request);
        return camioneroMapper.toDto(camioneroRepository.save(camionero));
    }

    public CamioneroResponseDTO obtenerCamioneroPorId(Long id) {
        Camionero camionero = camioneroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camionero no encontrado con ID: " + id));
        return camioneroMapper.toDto(camionero);
    }

    public List<CamioneroResponseDTO> obtenertodosLosCamioneros() {
        return camioneroRepository.findAll().stream()
                .map(camioneroMapper::toDto)
                .collect(Collectors.toList());
    }

    public CamioneroResponseDTO actualizarCamionero(Long id, CamioneroRequestDTO request) {
        Camionero camionero = camioneroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camionero no encontrado con ID: " + id));
        if (!camionero.getDni().equals(request.getDni()) && camioneroRepository.existsByDni(request.getDni())) {
            throw new IllegalArgumentException("El camionero con DNI " + request.getDni() + " ya existe.");
        }
        camionero.setNombre(request.getNombre());
        camionero.setApellido(request.getApellido());
        camionero.setDni(request.getDni());
        camionero.setTelefono(request.getTelefono());
        camionero.setEmail(request.getEmail());
        camionero.setNumeroLicencia(request.getNumeroLicencia());
        camionero.setHabilitado(request.getHabilitado());
        return camioneroMapper.toDto(camioneroRepository.save(camionero));
    }

    public void eliminarCamionero(Long id) {
        if (!camioneroRepository.existsById(id)) {
            throw new IllegalArgumentException("Camionero no encontrado con ID: " + id);
        }
        camioneroRepository.deleteById(id);
    }

    public List<CamioneroResponseDTO> obtenerCamionerosHabilitados() {
        return camioneroRepository.findByHabilitadoTrue().stream()
                .map(camioneroMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CamioneroResponseDTO> obtenerCamionerosporEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con ID: " + empresaId));
        return empresa.getCamioneros().stream()
                .map(camioneroMapper::toDto)
                .collect(Collectors.toList());
    }
}