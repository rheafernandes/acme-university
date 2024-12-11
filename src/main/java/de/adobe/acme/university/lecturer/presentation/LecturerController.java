package de.adobe.acme.university.lecturer.presentation;

import de.adobe.acme.university.lecturer.application.LecturerService;
import de.adobe.acme.university.lecturer.presentation.dto.LecturerRequest;
import de.adobe.acme.university.lecturer.presentation.dto.LecturerResponse;
import de.adobe.acme.university.student.presentation.StudentMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecturers")
@Validated
public class LecturerController {
    private final LecturerService lecturerService;
    private final LecturerMapper lecturerMapper;
    private final StudentMapper studentMapper;

    @PostMapping("")
    public ResponseEntity<LecturerResponse> createLecturer(@NotNull @Valid @RequestBody LecturerRequest request) {
        var savedLecturer = lecturerService.createLecturer(lecturerMapper.toLecturer(request));
        return ResponseEntity.ok(lecturerMapper.toResponse(savedLecturer));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<LecturerResponse> getLecturer(@PathVariable Long studentId) {
        var lecturer = lecturerService.findLecturerById(studentId);
        var lecturerResponse = lecturerMapper.toResponse(lecturer);
        if (lecturer.assignedStudents() != null) {
            var lecturerResponseWithStudents = lecturerResponse.toBuilder().assignedStudents(lecturer.assignedStudents().stream().map(studentMapper::toResponse).collect(Collectors.toSet())).build();
            return ResponseEntity.ok(lecturerResponseWithStudents);
        }
        return ResponseEntity.ok(lecturerResponse);
    }
}
