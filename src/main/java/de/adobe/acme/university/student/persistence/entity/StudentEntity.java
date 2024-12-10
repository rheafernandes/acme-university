package de.adobe.acme.university.student.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.adobe.acme.university.student.lecturer.persistence.entity.LecturerEntity;
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
@Table(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, updatable = false, unique = true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_lecturers",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
    )
    @Builder.Default
    @JsonManagedReference
    private Set<LecturerEntity> assignedLecturers = new HashSet<>();
}
