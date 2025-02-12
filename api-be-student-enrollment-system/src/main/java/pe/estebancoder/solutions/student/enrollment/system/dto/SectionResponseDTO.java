package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

@Data
public class SectionResponseDTO {
    private Long id;
    private String schedule;
    private int maxCapacity;
    private String courseName;
    private String instructorName;
}
