package com.transportista.operaciones.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.transportista.operaciones.service.CamioneroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.transportista.operaciones.dto.response.CamioneroResponseDTO;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.transportista.operaciones.dto.request.CamioneroRequestDTO;

@RestController
@RequestMapping("api/camioneros")
@RequiredArgsConstructor
public class CamioneroController {
    private final CamioneroService camioneroService;

    // Endpoint para listar todos los camioneros
    @GetMapping
    public List<CamioneroResponseDTO> listarTodosLosCamioneros() {
        return camioneroService.obtenertodosLosCamioneros();
    }

    // Endpoint para obtener un camionero por su ID
    @GetMapping("/{id}")
    public CamioneroResponseDTO obtenerCamioneroPorId(@PathVariable Long id) {
        return camioneroService.obtenerCamioneroPorId(id);
    }

    // Endpoint para crear un nuevo camionero
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CamioneroResponseDTO crearCamionero(@Valid @RequestBody CamioneroRequestDTO request) {
        return camioneroService.crearCamionero(request);
    }

    // Endpoint para actualizar un camionero existente
    @PutMapping("/{id}")
    public CamioneroResponseDTO actualizarCamionero(@PathVariable Long id,
            @Valid @RequestBody CamioneroRequestDTO dto) {
        return camioneroService.actualizarCamionero(id, dto);
    }

    // Endpoint para eliminar un camionero por su ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void EliminarCamionero(@PathVariable Long id) {
        camioneroService.eliminarCamionero(id);
    }
}
