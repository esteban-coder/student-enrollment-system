package pe.estebancoder.solutions.student.enrollment.system.repository.projection;

public interface CourseInfo {
    // Datos del curso
    Long getCourseId();
    String getCourseCode();
    String getCourseName();
    String getCourseDescription();
    int getCourseCredits();
    String getCoursePrerequisites();
    String getCourseStatus();

    // Datos de la sección
    Long getSectionId();
    String getSectionCode();
    String getAcademicPeriod();
    String getSchedule();
    String getRoomNumber();
    int getMaxCapacity();
    int getEnrolledStudentCount();
    String getSectionStatus();

    // Datos del instructor
    Long getInstructorId();
    String getInstructorName();
    String getInstructorEmail();
    String getInstructorPhoneNumber();
    String getAcademicDegree();
}
