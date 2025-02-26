package pe.estebancoder.solutions.student.enrollment.system.repository.projection;

public interface EnrollmentDetailProjection {

    // Campos de la tabla TBL_ENROLLMENT_DETAIL
    Long getEnrollmentDetailId();
    String getEnrollDetailStatus();
    String getGradeStatus();

    // Campos de la tabla TBL_SECTION
    String getSectionCode();
    String getSchedule();
    String getRoomNumber();

    // Campos de la tabla TBL_COURSE
    String getCourseCode();
    String getCourseName();
    Integer getCourseCredits();

    // Campos de la tabla TBL_INSTRUCTOR
    String getInstructorFullname();
}
