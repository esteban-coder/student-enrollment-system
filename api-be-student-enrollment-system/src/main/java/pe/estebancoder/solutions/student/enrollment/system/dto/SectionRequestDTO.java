package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

@Data
public class SectionRequestDTO {
    private String schedule;
    private int maxCapacity;
    private Long courseId;
    private Long instructorId;
}
