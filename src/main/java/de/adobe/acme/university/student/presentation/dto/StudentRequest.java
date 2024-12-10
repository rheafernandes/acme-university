package de.adobe.acme.university.student.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record StudentRequest(Long id,
                             @NotBlank
                             @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name must be alphanumeric")
                             String name,
                             @NotBlank
                             @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Surname must be alphanumeric")
                             String surname,
                             String email) {
}
