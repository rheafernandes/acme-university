package de.adobe.acme.university.lecturer.application;

import de.adobe.acme.university.common.exception.AlreadyExistsException;
import de.adobe.acme.university.common.exception.NotFoundException;
import de.adobe.acme.university.lecturer.domain.Lecturer;
import de.adobe.acme.university.lecturer.domain.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LecturerService {
    private final LecturerRepository lecturerRepository;

    public Lecturer createLecturer(Lecturer lecturer) {
        if (lecturerRepository.existsByLecturerEmail(lecturer.email())) {
            throw new AlreadyExistsException("Lecturer already exists");
        }
        return lecturerRepository.save(lecturer);
    }

    public Lecturer findLecturerById(Long lecturerId) {
        return lecturerRepository.findByLecturerId(lecturerId)
                .orElseThrow(() -> new NotFoundException("Lecturer not found"));
    }
}
