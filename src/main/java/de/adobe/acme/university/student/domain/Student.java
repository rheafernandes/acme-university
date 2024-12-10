package de.adobe.acme.university.student.domain;

import de.adobe.acme.university.student.lecturer.domain.Lecturer;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Builder(toBuilder = true)
public record Student(
        Long id,
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name must be alphanumeric")
        String name,
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Surname must be alphanumeric")
        String surname,
        String email,
        Set<Lecturer> assignedLecturers) {
}
