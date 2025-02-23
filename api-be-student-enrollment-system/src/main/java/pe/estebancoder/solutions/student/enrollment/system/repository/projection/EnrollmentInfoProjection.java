package pe.estebancoder.solutions.student.enrollment.system.repository.projection;

import java.time.LocalDateTime;

public interface EnrollmentInfoProjection {
    // Campos de la tabla TBL_ENROLLMENT
    Long getEnrollmentId();
    String getAcademicPeriod();
    String getEnrollStatus();
    Integer getTotalCredits();
    // java.sql.Timestamp getEnrollmentDate();  // tambien funciona pero tengo que hacer la conversion para que muestre una info adecuada al cliente
    LocalDateTime getEnrollmentDate(); // Tipo adecuado para las fechas

    // Campos de la tabla TBL_STUDENT (ts2)
    String getStudentCode();
    String getStudentFullname();

    // Campos de la tabla TBL_ENROLLMENT_DETAIL
    Long getEnrollmentDetailId();
    String getGradeStatus();
    String getEnrollDetailStatus();

    // Campos de la tabla TBL_SECTION
    String getSectionCode();
    String getSchedule();
    String getRoomNumber();

    // Campos de la tabla TBL_COURSE
    String getCourseName();
    String getCourseCode();
    Integer getCourseCredits();

    // Campos de la tabla TBL_INSTRUCTOR
    String getInstructorFullname();
}


