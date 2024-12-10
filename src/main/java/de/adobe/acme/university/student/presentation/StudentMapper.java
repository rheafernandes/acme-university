package de.adobe.acme.university.student.presentation;

import de.adobe.acme.university.student.domain.Student;
import de.adobe.acme.university.student.persistence.entity.StudentEntity;
import de.adobe.acme.university.student.presentation.dto.StudentRequest;
import de.adobe.acme.university.student.presentation.dto.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "email", source = "email")
    StudentEntity toEntity(Student student);

    @Mapping(target = "assignedLecturers", ignore = true)
    Student toStudent(StudentEntity student);

    @Mapping(target = "assignedLecturers", ignore = true)
    StudentResponse toResponse(Student student);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedLecturers", ignore = true)
    Student toStudent(StudentRequest student);
}