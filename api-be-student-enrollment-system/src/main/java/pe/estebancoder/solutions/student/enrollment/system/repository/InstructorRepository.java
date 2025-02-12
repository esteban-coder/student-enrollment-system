package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.estebancoder.solutions.student.enrollment.system.entity.InstructorEntity;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {
}
