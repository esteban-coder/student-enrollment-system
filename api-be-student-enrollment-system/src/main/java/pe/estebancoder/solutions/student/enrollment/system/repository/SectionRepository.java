package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.estebancoder.solutions.student.enrollment.system.entity.SectionEntity;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    // Custom queries si son necesarias
}
