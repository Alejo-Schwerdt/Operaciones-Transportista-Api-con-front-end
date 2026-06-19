package com.transportista.operaciones.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.transportista.operaciones.dto.request.ViajeRequestDTO;
import com.transportista.operaciones.dto.response.ViajeResponseDTO;
import com.transportista.operaciones.service.ViajeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/viajes")
@RequiredArgsConstructor
public class ViajeController {
    private final ViajeService viajeService;

    //GET /api/viajes → listar todos
    @GetMapping
    public List<ViajeResponseDTO> listarTodosLosViajes() {
        return viajeService.listarViajes();
    }
    //GET /api/viajes/{id} → buscar por id
    @GetMapping("/{id}")
    public ViajeResponseDTO obtenerViajePorId(@PathVariable Long id) {
        return viajeService.obtenerViajePorId(id);
    }
    //GET /api/viajes/camionero/{camioneroId} → viajes de un camionero
    @GetMapping("/camionero/{camioneroId}")
    public List<ViajeResponseDTO> obtenerViajesPorCamioneroId(@PathVariable Long camioneroId) {
        return viajeService.obtenerViajesPorCamioneroId(camioneroId);
    }
    //POST /api/viajes → crear
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViajeResponseDTO crearViaje(@Valid @RequestBody ViajeRequestDTO request) {
        return viajeService.crearViaje(request);
    }
    //PUT /api/viajes/{id} → actualizar
    @PutMapping("/{id}")
    public ViajeResponseDTO actualizarViaje(@PathVariable Long id, @Valid @RequestBody ViajeRequestDTO request) {
        return viajeService.actualizarViaje(id, request);
    }
    //DELETE /api/viajes/{id} → eliminar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarViaje(@PathVariable Long id) {
        viajeService.eliminarViaje(id);
    }
}
