package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentEntity;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    List<EnrollmentEntity> findByStudentId(Long studentId);
    List<EnrollmentEntity> findBySectionId(Long sectionId);

    // language=SQL
    @Query(value= """
        SELECT 
            te.ENROLLMENT_ID AS EnrollmentId,
            ti.NAME as InstructorName, 
            tc.NAME as CourseName, 
            ts.SCHEDULE as ScheduleSection, 
            ts2.DNI as StudentDNI, 
            ts2.NAME as StudentName, 
            te.type as EnrollmentType 
        FROM TBL_ENROLLMENT te
        INNER JOIN TBL_SECTION ts ON te.SECTION_ID = ts.SECTION_ID
        INNER JOIN TBL_COURSE tc ON ts.COURSE_ID = tc.COURSE_ID
        INNER JOIN TBL_INSTRUCTOR ti ON ti.INSTRUCTOR_ID = ts.INSTRUCTOR_ID
        INNER JOIN TBL_STUDENT ts2 ON ts2.STUDENT_ID = te.STUDENT_ID 
    """, nativeQuery = true)
    List<EnrollmentProjection> getAllEnrollments();

}
