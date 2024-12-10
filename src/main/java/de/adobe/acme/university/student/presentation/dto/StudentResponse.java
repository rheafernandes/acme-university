package de.adobe.acme.university.student.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.adobe.acme.university.student.lecturer.presentation.dto.LecturerResponse;
import lombok.Builder;

import java.util.Set;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StudentResponse(
        Long id,
        String name,
        String surname,
        String email,
        Set<LecturerResponse> assignedLecturers
) {
}
