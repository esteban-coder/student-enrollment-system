package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.estebancoder.solutions.student.enrollment.system.entity.SectionEntity;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {

    @Modifying
    @Query(value = """
        UPDATE TBL_SECTION SET
        ENROLLED_STUDENT_COUNT = ENROLLED_STUDENT_COUNT + 1
        WHERE SECTION_ID = :sectionId
    """, nativeQuery = true)
    void updateEnrolledStudentCount(Long sectionId);

    Integer getEnrolledStudentCountById(Long sectionId);

    @Query(value = """
        SELECT  
            CASE WHEN (MAX_CAPACITY - ENROLLED_STUDENT_COUNT) > 0 THEN 'TRUE'
            ELSE 'FALSE'
            END AS has_capacity_available
        FROM TBL_SECTION
        WHERE SECTION_ID = :sectionId
    """, nativeQuery = true)
    boolean availableForEnrollment(Long sectionId);

}
