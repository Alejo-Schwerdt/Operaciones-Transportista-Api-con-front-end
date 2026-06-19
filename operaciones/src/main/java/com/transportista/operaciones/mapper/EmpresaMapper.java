package com.transportista.operaciones.mapper;

import com.transportista.operaciones.dto.request.EmpresaRequestDTO;
import com.transportista.operaciones.dto.response.EmpresaResponseDTO;
import com.transportista.operaciones.entity.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    // Convierte RequestDTO → Entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "camioneros", ignore = true)
    Empresa toEntity(EmpresaRequestDTO dto);

    // Convierte Entidad → ResponseDTO
    EmpresaResponseDTO toDto(Empresa empresa);
}