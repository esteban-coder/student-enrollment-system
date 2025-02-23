package pe.estebancoder.solutions.student.enrollment.system.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnrollmentInfoDTO {
    private Long enrollmentId;
    private String academicPeriod;
    private String enrollmentStatus;  // Convertido de código a String
    private Integer totalCredits;
    private LocalDateTime enrollmentDate;

    // Student info
    private String studentCode;
    private String studentFullName;

    // Enrollment Detail info
    private Long enrollmentDetailId;
    private String gradeStatus;      // Convertido de código a String
    private String detailStatus;     // Convertido de código a String

    // Section info
    private String sectionCode;
    private String schedule;
    private String roomNumber;

    // Course info
    private String courseName;
    private String courseCode;
    private Integer courseCredits;

    // Instructor info
    private String instructorFullName;
}
