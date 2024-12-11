package de.adobe.acme.university.lecturer.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository {
    Optional<Lecturer> findByLecturerId(Long lecturerId);

    Lecturer save(Lecturer lecturer);

    boolean existsByLecturerEmail(String email);

    void deleteAll();
}
