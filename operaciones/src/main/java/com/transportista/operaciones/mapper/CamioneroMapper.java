package com.transportista.operaciones.mapper;

import com.transportista.operaciones.dto.request.CamioneroRequestDTO;
import com.transportista.operaciones.dto.response.CamioneroResponseDTO;
import com.transportista.operaciones.dto.response.CamioneroResumenDTO;
import com.transportista.operaciones.entity.Camionero;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CamioneroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresas", ignore = true)
    Camionero toEntity(CamioneroRequestDTO dto);

    CamioneroResponseDTO toDto(Camionero camionero);

    CamioneroResumenDTO toResumenDto(Camionero camionero);

    List<CamioneroResponseDTO> toDtoList(List<Camionero> camioneros);

    List<CamioneroResumenDTO> toResumenDtoList(List<Camionero> camioneros);
}