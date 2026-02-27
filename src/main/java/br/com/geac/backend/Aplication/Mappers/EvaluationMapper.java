package br.com.geac.backend.Aplication.Mappers;

import br.com.geac.backend.Aplication.DTOs.Reponse.EvaluationResponseDTO;
import br.com.geac.backend.Aplication.DTOs.Request.EvaluationRequestDTO;
import br.com.geac.backend.Domain.Entities.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {

    @Mapping(source = "registration.id", target = "registrationId")
    @Mapping(source = "registration.event.id", target = "eventId")
    @Mapping(source = "registration.event.title", target = "eventTitle")
    @Mapping(source = "registration.user.id", target = "userId")
    @Mapping(source = "registration.user.name", target = "userName")
    EvaluationResponseDTO toDTO(Evaluation evaluationResponseDTO);
}
