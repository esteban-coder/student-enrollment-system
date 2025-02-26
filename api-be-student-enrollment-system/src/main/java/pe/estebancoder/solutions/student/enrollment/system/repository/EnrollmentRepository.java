package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentInfoProjection;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentEntity;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    Optional<EnrollmentEntity> findByStudentIdAndAcademicPeriod(Long studentId, String academicPeriod);

    // language=SQL
    @Query(value= """
        SELECT
            te.ENROLLMENT_ID,
            ts2.STUDENT_CODE, ts2.FULLNAME AS STUDENT_FULLNAME,
            te.ACADEMIC_PERIOD, te.TOTAL_CREDITS, te.TOTAL_ENROLLED_COURSES, te.COMMENTS,  te.ENROLLMENT_DATE, te.STATUS AS ENROLL_STATUS,
            ted.ENROLLMENT_DETAIL_ID, ted.STATUS AS ENROLL_DETAIL_STATUS, ted.GRADE_STATUS,
            ts.SECTION_CODE, ts.SCHEDULE, ts.ROOM_NUMBER,
            tc.COURSE_CODE, tc.NAME AS COURSE_NAME, tc.CREDITS AS COURSE_CREDITS,
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
    List<EnrollmentInfoProjection> getAll(String studentCode, String academicPeriod);

    @Query(value= """
        SELECT
            te.ENROLLMENT_ID,
            ts2.STUDENT_CODE, ts2.FULLNAME AS STUDENT_FULLNAME,
            te.ACADEMIC_PERIOD, te.TOTAL_CREDITS, te.TOTAL_ENROLLED_COURSES, te.COMMENTS,  te.ENROLLMENT_DATE, te.STATUS AS ENROLL_STATUS
        FROM TBL_ENROLLMENT te
        INNER JOIN TBL_STUDENT ts2 ON ts2.STUDENT_ID = te.STUDENT_ID
        WHERE 
            ts2.STUDENT_CODE = :studentCode AND
            te.ACADEMIC_PERIOD = :academicPeriod
    """, nativeQuery = true)
    EnrollmentProjection getBy(String studentCode, String academicPeriod);

}
