package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = """
            SELECT 
                ENROLLMENT_ID, te.STUDENT_ID, ACADEMIC_PERIOD, TOTAL_CREDITS, TOTAL_ENROLLED_COURSES, 
                COMMENTS, ENROLLMENT_DATE, te.STATUS, TOTAL_CREDITS_EARNED, WEIGHTED_AVERAGE
            FROM TBL_ENROLLMENT te
            WHERE (te.STUDENT_ID = :studentId OR :studentId IS NULL)
            """, nativeQuery = true)
    List<EnrollmentEntity> getAllByStudent_Id(Long studentId);

    List<EnrollmentEntity> findAllByStudent_Id(Long studentId);

    /*
    @Query("""
        SELECT DISTINCT e
        FROM EnrollmentEntity e
        JOIN FETCH e.student s
        JOIN FETCH e.details ed
        JOIN FETCH ed.section sec
        JOIN FETCH sec.course c
        JOIN FETCH sec.instructor i
        WHERE
            (s.studentCode = :studentCode OR :studentCode IS NULL) AND
            (e.academicPeriod = :academicPeriod OR :academicPeriod IS NULL)
    """)
    */
    @Query("""
        SELECT DISTINCT e
        FROM EnrollmentEntity e
        JOIN FETCH e.student s
        JOIN FETCH e.details ed
        JOIN ed.section sec
        JOIN sec.course c
        JOIN sec.instructor i
        WHERE
            (s.studentCode = :studentCode OR :studentCode IS NULL) AND
            (e.academicPeriod = :academicPeriod OR :academicPeriod IS NULL)
    """)
    List<EnrollmentEntity> searchAll(@Param("studentCode") String studentCode, @Param("academicPeriod") String academicPeriod);
}
