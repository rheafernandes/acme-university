package de.adobe.acme.university.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adobe.acme.university.student.domain.Student;
import de.adobe.acme.university.student.domain.StudentRepository;
import de.adobe.acme.university.lecturer.domain.Lecturer;
import de.adobe.acme.university.lecturer.domain.LecturerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() {
        studentRepository.deleteAll();
        lecturerRepository.deleteAll();
    }


    @Test
    public void testAddStudentToLecturer_Success() throws Exception {
        var lecturer = Lecturer.builder().name("John").surname("Doe").email("john.doe@acme.de").build();
        var savedLecturer = lecturerRepository.save(lecturer);

        var student = Student.builder().name("Alice").surname("Smith").email("alice.smith@acme.de").build();

        mockMvc.perform(post("/api/v1/students/add/{lecturerId}", savedLecturer.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.surname").value("Smith"));
    }

    @Test
    public void testAddStudentToLecturer_NotFound() throws Exception {
        var lecturer = Lecturer.builder().name("John").surname("Doe").email("john.doe@acme.de").build();

        var student = Student.builder().name("Alice").surname("Smith").email("alice.smith@acme.de").build();

        mockMvc.perform(post("/api/v1/students/add/{lecturerId}", lecturer.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddStudentToLecturer_StudentAlreadyExists() throws Exception {
        var lecturer = Lecturer.builder().name("John").surname("Doe").email("john.doe@acme.de").build();
        var savedLecturer = lecturerRepository.save(lecturer);

        var student = Student.builder().name("Alice").surname("Smith").email("alice.smith@acme.de").build();
        studentRepository.save(student);

        mockMvc.perform(post("/api/v1/students/add/{lecturerId}", savedLecturer.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isConflict());
    }

}
