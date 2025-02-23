package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentDetailEntity;

public interface EnrollmentDetailRepository extends JpaRepository<EnrollmentDetailEntity, Long> {

    Integer countBySection_Id(Long sectionId);

    Integer countBySection_IdAndStatusIsNot(Long sectionId, String status);

}
