package de.adobe.acme.university.student.persistence.repository;

import de.adobe.acme.university.student.lecturer.persistence.entity.LecturerEntity;
import de.adobe.acme.university.student.lecturer.presentation.LecturerMapper;
import de.adobe.acme.university.student.domain.Student;
import de.adobe.acme.university.student.domain.StudentRepository;
import de.adobe.acme.university.student.persistence.repository.jpa.StudentJpaRepository;
import de.adobe.acme.university.student.presentation.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private final StudentJpaRepository studentJpaRepository;
    private final StudentMapper studentMapper;
    private final LecturerMapper lecturerMapper;

    @Override
    public Optional<Student> findByStudentId(Long studentId) {
        var studentEntity = studentJpaRepository.findById(studentId);
        if (studentEntity.isPresent()) {
            Set<LecturerEntity> assignedLecturers = studentEntity.get().getAssignedLecturers();
            var student = studentMapper.toStudent(studentEntity.get());
            student = student.toBuilder()
                    .assignedLecturers(assignedLecturers.stream()
                            .map(lecturerMapper::toLecturer)
                            .collect(Collectors.toSet()))
                    .build();
            return Optional.of(student);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Student save(Student student) {
        var studentEntity = studentMapper.toEntity(student);
        if (student.assignedLecturers() != null) {
            studentEntity.setAssignedLecturers(
                    student.assignedLecturers()
                            .stream()
                            .map(lecturerMapper::toEntity).collect(Collectors.toSet()));
        }
        return studentMapper.toStudent(studentJpaRepository.save(studentEntity));
    }

    @Override
    public void deleteAll() {
        studentJpaRepository.deleteAll();
    }

}
