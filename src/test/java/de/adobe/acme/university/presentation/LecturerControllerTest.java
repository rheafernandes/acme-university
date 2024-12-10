package de.adobe.acme.university.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adobe.acme.university.student.lecturer.domain.Lecturer;
import de.adobe.acme.university.student.lecturer.domain.LecturerRepository;
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
public class LecturerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() {
        lecturerRepository.deleteAll();
    }

    @Test
    public void testCreateLecturer_Success() throws Exception {
        var lecturer = Lecturer.builder().name("John").surname("Doe").email("john.doe@acme.de").build();
        mockMvc.perform(post("/api/v1/lecturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lecturer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    public void testCreateLecturer_AlreadyExists() throws Exception {
        var lecturer = Lecturer.builder().name("John").surname("Doe").email("john.doe@acme.de").build();
        lecturerRepository.save(lecturer);

        mockMvc.perform(post("/api/v1//lecturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lecturer)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Lecturer already exists"));
    }

    @Test
    public void testGetLecturerById_Success() throws Exception {
        var lecturer = Lecturer.builder().name("John").surname("Doe").email("john.doe@acme.de").build();
        Lecturer savedLecturer = lecturerRepository.save(lecturer);

        mockMvc.perform(get("/api/v1/lecturers/{id}", savedLecturer.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    public void testGetLecturerById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1//lecturers/{id}", 999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Lecturer not found"));
    }
}
