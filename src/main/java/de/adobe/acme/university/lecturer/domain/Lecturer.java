package de.adobe.acme.university.lecturer.domain;

import de.adobe.acme.university.student.domain.Student;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Builder(toBuilder = true)
public record Lecturer(
        Long id,
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name must be alphanumeric")
        String name,
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Surname must be alphanumeric")
        String surname,
        String email,
        Set<Student> assignedStudents) {
}
