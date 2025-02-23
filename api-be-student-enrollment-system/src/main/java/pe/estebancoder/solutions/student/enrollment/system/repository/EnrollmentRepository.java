package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentInfoProjection;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    Optional<EnrollmentEntity> findByStudentId(Long studentId);
    Optional<EnrollmentEntity> findByStudentIdAndAcademicPeriod(Long studentId, String academicPeriod);
    // List<EnrollmentEntity> findBySectionId(Long sectionId);

    // language=SQL
    @Query(value= """
        SELECT
            te.ENROLLMENT_ID,
            ts2.STUDENT_CODE, ts2.FULLNAME AS STUDENT_FULLNAME,
            te.ENROLLMENT_DATE, te.ACADEMIC_PERIOD, te.STATUS AS ENROLL_STATUS, te.TOTAL_CREDITS,
            ted.ENROLLMENT_DETAIL_ID, ted.GRADE_STATUS, ted.STATUS AS ENROLL_DETAIL_STATUS,
            ts.SECTION_CODE, ts.SCHEDULE, ts.ROOM_NUMBER,
            tc.NAME AS COURSE_NAME, tc.COURSE_CODE, tc.CREDITS AS COURSE_CREDITS,
            ti.FULLNAME AS INSTRUCTOR_FULLNAME
        FROM TBL_ENROLLMENT te
        INNER JOIN TBL_ENROLLMENT_DETAIL ted ON te.ENROLLMENT_ID = ted.ENROLLMENT_ID
        INNER JOIN TBL_SECTION ts ON ted.SECTION_ID = ts.SECTION_ID
        INNER JOIN TBL_COURSE tc ON ts.COURSE_ID = tc.COURSE_ID
        INNER JOIN TBL_INSTRUCTOR ti ON ti.INSTRUCTOR_ID = ts.INSTRUCTOR_ID
        INNER JOIN TBL_STUDENT ts2 ON ts2.STUDENT_ID = te.STUDENT_ID
        WHERE 
            (ts2.STUDENT_CODE = :studentCode OR :studentCode IS NULL) AND
            (te.ACADEMIC_PERIOD = :academicPeriod OR :academicPeriod IS NULL)
    """, nativeQuery = true)
    List<EnrollmentInfoProjection> getAllEnrollmentInfo(String studentCode, String academicPeriod);

}
