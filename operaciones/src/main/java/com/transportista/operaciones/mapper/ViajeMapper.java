package com.transportista.operaciones.mapper;

import com.transportista.operaciones.dto.request.ViajeRequestDTO;
import com.transportista.operaciones.dto.response.ViajeResponseDTO;
import com.transportista.operaciones.entity.Viaje;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ViajeMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaLlegada", ignore = true)
    @Mapping(target = "camionero", ignore = true)
    @Mapping(target = "camion", ignore = true)
    Viaje toEntity(ViajeRequestDTO dto);

    // Entity -> DTO
    @Mapping(target = "camioneroId", source = "camionero.id")
    @Mapping(target = "nombreCamionero", expression =
            "java(viaje.getCamionero() != null ? viaje.getCamionero().getNombre() + \" \" + viaje.getCamionero().getApellido() : null)")
    @Mapping(target = "camionId", source = "camion.id")
    @Mapping(target = "patenteCamion", source = "camion.patente")
    ViajeResponseDTO toDto(Viaje viaje);
}