package de.adobe.acme.university.student.presentation;

import de.adobe.acme.university.student.application.StudentService;
import de.adobe.acme.university.lecturer.presentation.LecturerMapper;
import de.adobe.acme.university.student.presentation.dto.StudentRequest;
import de.adobe.acme.university.student.presentation.dto.StudentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
@Validated
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final LecturerMapper lecturerMapper;

    @PostMapping("/add/{lecturerId}")
    public ResponseEntity<StudentResponse> addStudent(@NotNull @PathVariable Long lecturerId, @NotNull @Valid @RequestBody StudentRequest request) {
        var student = studentService.addStudent(lecturerId, studentMapper.toStudent(request));
        return ResponseEntity.ok(studentMapper.toResponse(student));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Long studentId) {
        var student = studentService.getStudent(studentId);
        var studentResponse = studentMapper.toResponse(student);
        if (student.assignedLecturers() != null) {
            var studentResponseWithLecturers = studentResponse.toBuilder().assignedLecturers(
                    student.assignedLecturers().stream().map(lecturerMapper::toResponse).collect(Collectors.toSet())).build();
            return ResponseEntity.ok(studentResponseWithLecturers);
        }
        return ResponseEntity.ok(studentResponse);
    }
}
