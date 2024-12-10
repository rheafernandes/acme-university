package de.adobe.acme.university.student.lecturer.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LecturerRequest(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name must be alphanumeric")
        String name,
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Surname must be alphanumeric")
        String surname,
        String email) {
}
