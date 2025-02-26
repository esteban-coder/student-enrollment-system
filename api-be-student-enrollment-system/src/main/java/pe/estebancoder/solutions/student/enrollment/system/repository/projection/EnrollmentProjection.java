package pe.estebancoder.solutions.student.enrollment.system.repository.projection;

import java.time.LocalDateTime;

public interface EnrollmentProjection {

    // Campos de la tabla TBL_ENROLLMENT
    Long getEnrollmentId();
    String getAcademicPeriod();
    Integer getTotalCredits();
    Integer getTotalEnrolledCourses();
    String getComments();
    // java.sql.Timestamp getEnrollmentDate();  // tambien funciona pero tengo que hacer la conversion para que muestre una info adecuada al cliente
    LocalDateTime getEnrollmentDate(); // Tipo adecuado para las fechas
    String getEnrollStatus();

    // Campos de la tabla TBL_STUDENT (ts2)
    String getStudentCode();
    String getStudentFullname();
}
