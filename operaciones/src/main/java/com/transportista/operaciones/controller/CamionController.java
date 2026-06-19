package com.transportista.operaciones.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.transportista.operaciones.dto.request.CamionRequestDTO;
import com.transportista.operaciones.dto.response.CamionResponseDTO;
import com.transportista.operaciones.service.CamionService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/camiones")
@RequiredArgsConstructor
public class CamionController {
    private final CamionService camionService;

    // GET /api/camiones → listar todos
    @GetMapping
    public List<CamionResponseDTO> listarTodosLosCamiones() {
        return camionService.listarTodosLosCamiones();
    }

    // GET /api/camiones/{id} → buscar por id
    @GetMapping("/{id}")
    public CamionResponseDTO obtenerCamionPorId(@PathVariable Long id) {
        return camionService.obtenerCamionPorId(id);
    }

    // POST /api/camiones → crear
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CamionResponseDTO crearCamion(@Valid @RequestBody CamionRequestDTO dto) {
        return camionService.crearCamion(dto);
    }

    // PUT /api/camiones/{id} → actualizar
    @PutMapping("/{id}")
    public CamionResponseDTO actualizarCamion(@PathVariable Long id, @Valid @RequestBody CamionRequestDTO dto) {
        return camionService.actualizarCamion(id, dto);
    }

    // DELETE /api/camiones/{id} → eliminar
    @DeleteMapping("/{id}")
    public void eliminarCamion(@PathVariable Long id) {
        camionService.eliminarCamion(id);
    }

    // GET /api/camiones/stats → conteo de camiones en viaje
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getCamionStats() {
        return ResponseEntity.ok(camionService.obtenerEstadisticas());
    }

}
