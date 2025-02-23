package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentCodeSequenceEntity;

public interface StudentCodeSequenceRepository extends JpaRepository<StudentCodeSequenceEntity, Integer> {
}
