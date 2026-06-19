package com.transportista.operaciones.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.transportista.operaciones.dto.request.CamionRequestDTO;
import com.transportista.operaciones.dto.response.CamionResponseDTO;
import com.transportista.operaciones.entity.Camion;

@Mapper(componentModel = "spring")
public interface CamionMapper {

    @Mapping(target = "id", ignore = true)
    Camion toEntity(CamionRequestDTO dto);

    CamionResponseDTO toDto(Camion camion);
}
