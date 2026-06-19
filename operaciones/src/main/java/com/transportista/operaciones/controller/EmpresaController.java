package com.transportista.operaciones.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.transportista.operaciones.dto.request.EmpresaRequestDTO;
import com.transportista.operaciones.dto.response.CamioneroResponseDTO;
import com.transportista.operaciones.dto.response.EmpresaResponseDTO;
import com.transportista.operaciones.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;  
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("api/empresa")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService empresaservice;
    
    // Endpoint para listar todas las empresas
    @GetMapping
    public List<EmpresaResponseDTO> listarTodasLasEmpresas() {
        return empresaservice.ObtenerTodasLasEmpresas();
    }

    // Endpoint para obtener una empresa por su ID
    @GetMapping("/{id}")
    public EmpresaResponseDTO listarEmpresaPorId(@PathVariable Long id) {
        return empresaservice.obtenerEmpresaPorId(id);
    }

    // Endpoint para crear una nueva empresa
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaResponseDTO CrearEmpresa(@Valid @RequestBody EmpresaRequestDTO request) {
        return empresaservice.crearEmpresa(request);
    }

    // Endpoint para actualizar una empresa existente
    @PutMapping("/{id}")
    public EmpresaResponseDTO actualizarEmpresa(@PathVariable Long id, @Valid @RequestBody EmpresaRequestDTO dto) {
        return empresaservice.actualizarEmpresa(id, dto);
    }
    
    // Endpoint para eliminar una empresa por su ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void EliminarEmpresa(@PathVariable Long id) {    
        empresaservice.eliminarEmpresa(id);
    }

    // Endpoint para asignar un camionero a una empresa
        @PostMapping("/{empresaId}/camioneros/{camioneroId}")
    public CamioneroResponseDTO asignarCamionero(@PathVariable Long empresaId, @PathVariable Long camioneroId) {
        return empresaservice.asignarCamioneroAEmpresa(empresaId, camioneroId);
    }
}
