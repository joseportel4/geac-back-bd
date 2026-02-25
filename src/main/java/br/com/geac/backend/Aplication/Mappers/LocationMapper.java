package br.com.geac.backend.Aplication.Mappers;

import br.com.geac.backend.Aplication.DTOs.Reponse.LocationResponseDTO;
import br.com.geac.backend.Aplication.DTOs.Request.LocationPatchRequestDTO;
import br.com.geac.backend.Aplication.DTOs.Request.LocationRequestDTO;
import br.com.geac.backend.Domain.Entities.Location;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationResponseDTO toDto(Location location);

    @Mapping(target = "id", ignore = true)
    Location toEntity(LocationRequestDTO locationRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(LocationPatchRequestDTO dto, @MappingTarget Location location);
}
