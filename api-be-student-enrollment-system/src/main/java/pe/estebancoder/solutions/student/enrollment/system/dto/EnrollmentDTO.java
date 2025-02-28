package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EnrollmentDTO {

    private Long id;
    private StudentDTO student;
    private String academicPeriod;
    private Integer totalCredits;
    private Integer totalEnrolledCourses;
    private String comments;
    private LocalDateTime enrollmentDate;
    private String status;
    private List<EnrollmentDetailDTO> details;
    private Integer totalCreditsEarned;
    private BigDecimal weightedAverage;
}
