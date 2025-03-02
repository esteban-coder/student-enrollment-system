package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EnrollmentDetailDTO {

    private Long id;
    // private EnrollmentDTO enrollment;
    private SectionWithCourseDTO section;
    private Integer credits;
    private String status;
    private LocalDateTime withdrawalDate;
    private String withdrawalReason;
    private Integer creditsEarned;
    private BigDecimal finalGrade;
    private String gradeStatus;
}
