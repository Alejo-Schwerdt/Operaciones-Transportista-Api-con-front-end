package com.transportista.operaciones.service;

import com.transportista.operaciones.repository.EmpresaRepository;
import com.transportista.operaciones.repository.CamioneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.transportista.operaciones.dto.request.EmpresaRequestDTO;
import com.transportista.operaciones.dto.response.CamioneroResponseDTO;
import com.transportista.operaciones.dto.response.EmpresaResponseDTO;
import com.transportista.operaciones.entity.Empresa;
import com.transportista.operaciones.entity.Camionero;
import com.transportista.operaciones.mapper.EmpresaMapper;
import com.transportista.operaciones.mapper.CamioneroMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final CamioneroRepository camioneroRepository;
    private final EmpresaMapper empresaMapper;
    private final CamioneroMapper camioneroMapper;

    public EmpresaResponseDTO crearEmpresa(EmpresaRequestDTO request) {
        if (empresaRepository.existsByCuit(request.getCuit())) {
            throw new IllegalArgumentException("La empresa con CUIT " + request.getCuit() + " ya existe.");
        }
        Empresa empresa = empresaMapper.toEntity(request);
        return empresaMapper.toDto(empresaRepository.save(empresa));
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDTO obtenerEmpresaPorId(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con ID: " + id));
        return empresaMapper.toDto(empresa);
    }

    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> ObtenerTodasLasEmpresas() {
        return empresaRepository.findAll().stream()
                .map(empresaMapper::toDto)
                .collect(Collectors.toList());
    }

    public EmpresaResponseDTO actualizarEmpresa(Long id, EmpresaRequestDTO request) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con ID: " + id));
        if (!empresa.getCuit().equals(request.getCuit()) && empresaRepository.existsByCuit(request.getCuit())) {
            throw new IllegalArgumentException("La empresa con CUIT " + request.getCuit() + " ya existe.");
        }
        empresa.setNombre(request.getNombre());
        empresa.setCuit(request.getCuit());
        empresa.setDireccion(request.getDireccion());
        empresa.setTelefono(request.getTelefono());
        empresa.setEmail(request.getEmail());
        empresa.setPorcentajeRetencion(request.getPorcentajeRetencion());
        empresa.setActiva(request.getActiva());
        return empresaMapper.toDto(empresaRepository.save(empresa));
    }

    public void eliminarEmpresa(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new IllegalArgumentException("Empresa no encontrada con ID: " + id);
        }
        empresaRepository.deleteById(id);
    }

    public CamioneroResponseDTO asignarCamioneroAEmpresa(Long empresaId, Long camioneroId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con ID: " + empresaId));
        Camionero camionero = camioneroRepository.findById(camioneroId)
                .orElseThrow(() -> new IllegalArgumentException("Camionero no encontrado con ID: " + camioneroId));

        if (empresa.getCamioneros().contains(camionero)) {
            throw new IllegalArgumentException("El camionero ya está asignado a esta empresa");
        }

        empresa.getCamioneros().add(camionero);
        empresaRepository.save(empresa);
        return camioneroMapper.toDto(camionero);
    }
}