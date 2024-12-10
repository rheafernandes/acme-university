package de.adobe.acme.university.student.lecturer.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.adobe.acme.university.student.presentation.dto.StudentResponse;
import lombok.Builder;

import java.util.Set;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LecturerResponse(
        Long id,
        String name,
        String surname,
        String email,
        Set<StudentResponse> assignedStudents) {
}
