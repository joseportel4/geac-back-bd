package br.com.geac.backend.Aplication.DTOs.Request;

import jakarta.validation.constraints.*;

public record RequirementRequestDTO(

        @NotBlank
        @Size(min = 5,max = 255)
        String description
) {}
