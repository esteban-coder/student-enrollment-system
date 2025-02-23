package pe.estebancoder.solutions.student.enrollment.system.dto.response;

import lombok.Data;

@Data
public class EnrollmentDetailResponseDTO {
    private Long id;
    private Long sectionId;
    private String courseCode;
    private String courseName;
    private String sectionCode;
    private String schedule;
    private String instructorName;
    private Integer credits;
    private String status;
}
