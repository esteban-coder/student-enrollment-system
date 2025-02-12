package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnrollmentRequestDTO {
    private Long studentId;
    private List<Long> sectionIds;
}
