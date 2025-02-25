package pe.estebancoder.solutions.student.enrollment.system.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EnrollmentResponseDTO {
    private Long id;
    private Long studentId;
    private String studentCode;
    private String studentFullName;
    private String academicPeriod;
    private Integer totalCredits;
    private Integer totalEnrolledCourses;
    private String comments;
    private LocalDateTime enrollmentDate;
    private String status;
    private List<EnrollmentDetailResponseDTO> details;
}
