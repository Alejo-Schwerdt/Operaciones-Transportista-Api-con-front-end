package com.transportista.operaciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transportista.operaciones.dto.request.EmpresaRequestDTO;
import com.transportista.operaciones.dto.response.EmpresaResponseDTO;
import com.transportista.operaciones.repository.UsuarioRepository;
import com.transportista.operaciones.security.JwtService;
import com.transportista.operaciones.service.EmpresaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmpresaController.class) // Solo carga el controller, no toda la app
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula requests HTTP

    @Autowired
    private ObjectMapper objectMapper; // Convierte objetos a JSON

    @MockBean // Mock para Spring context
    private EmpresaService empresaService;

     @MockBean
    private JwtService jwtService;  

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    @WithMockUser // Simula un usuario autenticado
    void crearEmpresa_cuandoDatosValidos_debeRetornar201() throws Exception {
        // Arrange
        EmpresaRequestDTO request = new EmpresaRequestDTO();
        request.setNombre("Transportes SA");
        request.setCuit("30712345678");
        request.setDireccion("Av. Corrientes 1234");
        request.setTelefono("011-1234-5678");
        request.setEmail("info@empresa.com");
        request.setPorcentajeRetencion(15.0);
        request.setActiva(true);

        EmpresaResponseDTO response = new EmpresaResponseDTO();
        response.setId(1L);
        response.setNombre("Transportes SA");

        when(empresaService.crearEmpresa(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/empresa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Transportes SA"));
    }

    @Test
    @WithMockUser
    void crearEmpresa_cuandoNombreVacio_debeRetornar400() throws Exception {
        // Arrange — nombre vacío, viola @NotBlank
        EmpresaRequestDTO request = new EmpresaRequestDTO();
        request.setNombre(""); // vacío
        request.setCuit("30712345678");
        request.setPorcentajeRetencion(15.0);

        // Act & Assert
        mockMvc.perform(post("/api/empresa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}