package br.com.geac.backend.Aplication.Services;

import br.com.geac.backend.Aplication.DTOs.Reponse.RequirementsResponseDTO;
import br.com.geac.backend.Aplication.DTOs.Request.RequirementRequestDTO;
import br.com.geac.backend.Aplication.Mappers.RequirementMapper;
import br.com.geac.backend.Repositories.EventRequirementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequirementService {

    private final EventRequirementRepository repository;
    private final RequirementMapper mapper;

    @Transactional
    public RequirementsResponseDTO createRequirement(RequirementRequestDTO dto) {

        var toBeSaved = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(toBeSaved));
    }

    public RequirementsResponseDTO getById(Integer id) {
        var requirement = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));
        return mapper.toDTO(requirement);
    }

    public List<RequirementsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public RequirementsResponseDTO updateRequirement(Integer id, RequirementRequestDTO dto) {
        var requirement = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));
        if (dto.description() != null) requirement.setDescription(dto.description());
        return mapper.toDTO(requirement);
    }

    @Transactional
    public void deleteRequirement(Integer id) {
        if(!repository.existsById(id)) throw new EntityNotFoundException("Requirement not found");
        repository.deleteById(id);
        repository.flush();
    }
}
