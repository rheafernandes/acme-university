package de.adobe.acme.university.student.lecturer.persistence.repository.jpa;

import de.adobe.acme.university.student.lecturer.persistence.entity.LecturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerJpaRepository extends JpaRepository<LecturerEntity, Long> {

    Boolean existsByEmail(String email);
}
