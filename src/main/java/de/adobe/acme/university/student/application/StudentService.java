package de.adobe.acme.university.student.application;

import de.adobe.acme.university.common.exception.AlreadyExistsException;
import de.adobe.acme.university.common.exception.NotFoundException;
import de.adobe.acme.university.lecturer.domain.Lecturer;
import de.adobe.acme.university.lecturer.domain.LecturerRepository;
import de.adobe.acme.university.student.domain.Student;
import de.adobe.acme.university.student.domain.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;

    @Transactional
    public Student addStudent(Long lecturerId, Student student) {
        Lecturer lecturer = lecturerRepository.findByLecturerId(lecturerId)
                .orElseThrow(() -> new NotFoundException("Lecturer not found with id: " + lecturerId));
        Student managedStudent;
        if (student.id() != null) {
            managedStudent = getStudent(student.id());
        } else {
            managedStudent = student;
        }
        var updatedLecturer = lecturer.toBuilder().assignedStudents(
                Optional.ofNullable(lecturer.assignedStudents()).orElseGet(HashSet::new)
        ).build();
        updatedLecturer.assignedStudents().add(managedStudent);
        var updatedStudent = managedStudent.toBuilder().assignedLecturers(
                Optional.ofNullable(managedStudent.assignedLecturers()).orElseGet(HashSet::new)
        ).build();
        updatedStudent.assignedLecturers().add(lecturer);
        try {
            return studentRepository.save(updatedStudent);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("A student with this email already exists.");
        }
    }

    @Transactional(readOnly = true)
    public Student getStudent(Long studentId) {
        return studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));
    }
}
