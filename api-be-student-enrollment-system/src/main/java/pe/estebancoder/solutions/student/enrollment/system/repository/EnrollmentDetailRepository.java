package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentDetailEntity;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentDetailProjection;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentInfoProjection;

import java.util.List;

public interface EnrollmentDetailRepository extends JpaRepository<EnrollmentDetailEntity, Long> {

    Integer countBySection_Id(Long sectionId);

    Integer countBySection_IdAndStatusIsNot(Long sectionId, String status);

    @Query(value= """
        SELECT
            ted.ENROLLMENT_DETAIL_ID, ted.STATUS AS ENROLL_DETAIL_STATUS, ted.GRADE_STATUS,
            ts.SECTION_CODE, ts.SCHEDULE, ts.ROOM_NUMBER,
            tc.COURSE_CODE, tc.NAME AS COURSE_NAME, tc.CREDITS AS COURSE_CREDITS,
            ti.FULLNAME AS INSTRUCTOR_FULLNAME
        FROM TBL_ENROLLMENT_DETAIL ted
        INNER JOIN TBL_SECTION ts ON ted.SECTION_ID = ts.SECTION_ID
        INNER JOIN TBL_COURSE tc ON ts.COURSE_ID = tc.COURSE_ID
        INNER JOIN TBL_INSTRUCTOR ti ON ti.INSTRUCTOR_ID = ts.INSTRUCTOR_ID
        WHERE 
            ted.ENROLLMENT_ID = :enrollmentId
    """, nativeQuery = true)
    List<EnrollmentDetailProjection> getEnrollmentDetail(Long enrollmentId);
}
