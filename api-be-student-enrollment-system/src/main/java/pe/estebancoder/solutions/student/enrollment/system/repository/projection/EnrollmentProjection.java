package pe.estebancoder.solutions.student.enrollment.system.repository.projection;

public interface EnrollmentProjection {
    Long getEnrollmentId();
    String getInstructorName();
    String getCourseName();
    String getScheduleSection();
    String getStudentDNI();
    String getStudentName();
    String getEnrollmentType();
}
