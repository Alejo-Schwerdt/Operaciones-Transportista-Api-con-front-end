package com.transportista.operaciones.service;

import com.transportista.operaciones.dto.request.EmpresaRequestDTO;
import com.transportista.operaciones.dto.response.EmpresaResponseDTO;
import com.transportista.operaciones.entity.Empresa;
import com.transportista.operaciones.mapper.CamioneroMapper;
import com.transportista.operaciones.mapper.EmpresaMapper;
import com.transportista.operaciones.repository.CamioneroRepository;
import com.transportista.operaciones.repository.EmpresaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private EmpresaMapper empresaMapper;

    @Mock
    private CamioneroRepository camioneroRepository;

    @Mock
    private CamioneroMapper camioneroMapper;

    @InjectMocks
    private EmpresaService empresaService;

    @Test
    void crearEmpresa_cuandoDatosValidos_debeRetornarDTO() {
        // Arrange — preparamos los objetos de prueba
        EmpresaRequestDTO request = new EmpresaRequestDTO();
        request.setNombre("Transportes SA");
        request.setCuit("30712345678");
        request.setDireccion("Av. Corrientes 1234");
        request.setTelefono("011-1234-5678");
        request.setEmail("info@empresa.com");
        request.setPorcentajeRetencion(15.0);
        request.setActiva(true);

        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNombre("Transportes SA");
        empresa.setCuit("30712345678");

        EmpresaResponseDTO responseDTO = new EmpresaResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Transportes SA");
        responseDTO.setCuit("30712345678");

        
        when(empresaRepository.existsByCuit(request.getCuit())).thenReturn(false);
        when(empresaMapper.toEntity(request)).thenReturn(empresa);
        when(empresaRepository.save(empresa)).thenReturn(empresa);
        when(empresaMapper.toDto(empresa)).thenReturn(responseDTO);

       
        EmpresaResponseDTO resultado = empresaService.crearEmpresa(request);

        
        assertNotNull(resultado); // el resultado no es null
        assertEquals("Transportes SA", resultado.getNombre()); // el nombre es correcto
        assertEquals("30712345678", resultado.getCuit()); // el cuit es correcto
        verify(empresaRepository, times(1)).save(empresa); // se llamó save exactamente 1 vez
    }

    @Test
    void crearEmpresa_cuandoCuitDuplicado_debeLanzarExcepcion() {
        
        EmpresaRequestDTO request = new EmpresaRequestDTO();
        request.setNombre("Empresa Duplicada");
        request.setCuit("30712345678");

        
        when(empresaRepository.existsByCuit(request.getCuit())).thenReturn(true);

        
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> empresaService.crearEmpresa(request)
        );

        assertEquals(
            "La empresa con CUIT 30712345678 ya existe.",
            excepcion.getMessage()
        );

        // Verificamos que nunca se llamó a save porque falló antes
        verify(empresaRepository, never()).save(any());
    }
}
