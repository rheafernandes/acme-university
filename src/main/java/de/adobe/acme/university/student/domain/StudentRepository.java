package de.adobe.acme.university.student.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository {
    Optional<Student> findByStudentId(Long studentId);

    Student save(Student student);

    void deleteAll();

}
