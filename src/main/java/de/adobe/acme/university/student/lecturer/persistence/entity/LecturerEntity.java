package de.adobe.acme.university.student.lecturer.persistence.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import de.adobe.acme.university.student.persistence.entity.StudentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lecturer")
public class LecturerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false, updatable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "assignedLecturers", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonBackReference
    private Set<StudentEntity> assignedStudents = new HashSet<>();
}
