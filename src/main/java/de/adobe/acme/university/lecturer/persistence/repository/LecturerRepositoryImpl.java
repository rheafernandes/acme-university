package de.adobe.acme.university.lecturer.persistence.repository;

import de.adobe.acme.university.lecturer.domain.Lecturer;
import de.adobe.acme.university.lecturer.domain.LecturerRepository;
import de.adobe.acme.university.lecturer.persistence.repository.jpa.LecturerJpaRepository;
import de.adobe.acme.university.lecturer.presentation.LecturerMapper;
import de.adobe.acme.university.student.persistence.entity.StudentEntity;
import de.adobe.acme.university.student.presentation.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LecturerRepositoryImpl implements LecturerRepository {

    private final LecturerJpaRepository lecturerJpaRepository;
    private final LecturerMapper lecturerMapper;
    private final StudentMapper studentMapper;

    @Override
    public Optional<Lecturer> findByLecturerId(Long lecturerId) {
        var lecturerEntity = lecturerJpaRepository.findById(lecturerId);
        if (lecturerEntity.isPresent()) {
            Set<StudentEntity> assignedStudents = lecturerEntity.get().getAssignedStudents();
            var lecturer = lecturerMapper.toLecturer(lecturerEntity.get());
            lecturer = lecturer.toBuilder()
                    .assignedStudents(assignedStudents.stream()
                            .map(studentMapper::toStudent)
                            .collect(Collectors.toSet()))
                    .build();
            return Optional.of(lecturer);
        } else {
            // Handle the case when lecturerEntity is not present
            return Optional.empty();
        }
    }

    @Override
    public Lecturer save(Lecturer lecturer) {
        var entityToBeSaved = lecturerMapper.toEntity(lecturer);
        return lecturerMapper.toLecturer(lecturerJpaRepository.save(entityToBeSaved));
    }

    @Override
    public boolean existsByLecturerEmail(String email) {
        return lecturerJpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteAll() {
        lecturerJpaRepository.deleteAll();
    }
}
