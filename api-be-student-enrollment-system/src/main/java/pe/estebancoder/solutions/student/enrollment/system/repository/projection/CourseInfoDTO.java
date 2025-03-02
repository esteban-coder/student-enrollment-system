package pe.estebancoder.solutions.student.enrollment.system.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseInfoDTO {
    // Datos del curso
    private Long courseId;
    private String courseCode;
    private String courseName;
    private String courseDescription;
    private int courseCredits;
    private String coursePrerequisites;
    private String courseStatus;

    // Datos de la sección
    private Long sectionId;
    private String sectionCode;
    private String academicPeriod;
    // private LocalDate startDate;
    // private LocalDate endDate;
    private String schedule;
    private String roomNumber;
    private int maxCapacity;
    private int enrolledStudentCount;
    private String sectionStatus;

    // Datos del instructor
    private Long instructorId;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhoneNumber;
    private String academicDegree;
}
