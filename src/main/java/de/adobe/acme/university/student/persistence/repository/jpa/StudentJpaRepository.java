package de.adobe.acme.university.student.persistence.repository.jpa;

import de.adobe.acme.university.student.persistence.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentEntity, Long> {
}
