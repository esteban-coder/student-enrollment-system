package pe.estebancoder.solutions.student.enrollment.system.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnrollmentInfoDTO {
    // Enrollment info
    private Long enrollmentId;
    private String academicPeriod;
    private Integer totalCredits;
    private Integer totalEnrolledCourses;
    private String comments;
    private LocalDateTime enrollmentDate;
    private String enrollmentStatus;  // Convertido de código a String

    // Student info
    private String studentCode;
    private String studentFullName;

    // Enrollment Detail info
    private Long enrollmentDetailId;
    private String detailStatus;     // Convertido de código a String
    private String gradeStatus;      // Convertido de código a String // No es necesario este campo al matricularse, es util al termino del semestre

    // Section info
    private String sectionCode;
    private String schedule;
    private String roomNumber;

    // Course info
    private String courseCode;
    private String courseName;
    private Integer courseCredits;

    // Instructor info
    private String instructorFullName;
}
