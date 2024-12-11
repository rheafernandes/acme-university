package de.adobe.acme.university.lecturer.presentation;

import de.adobe.acme.university.lecturer.domain.Lecturer;
import de.adobe.acme.university.lecturer.persistence.entity.LecturerEntity;
import de.adobe.acme.university.lecturer.presentation.dto.LecturerRequest;
import de.adobe.acme.university.lecturer.presentation.dto.LecturerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LecturerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "email", source = "email")
    public abstract LecturerEntity toEntity(Lecturer lecturer);

    @Mapping(target = "assignedStudents", ignore = true)
    public abstract Lecturer toLecturer(LecturerEntity lecturerEntity);

    @Mapping(target = "assignedStudents", ignore = true)
    public abstract LecturerResponse toResponse(Lecturer lecturer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedStudents", ignore = true)
    public abstract Lecturer toLecturer(LecturerRequest lecturerRequest);
}
