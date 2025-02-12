package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnrollmentResponseDTO {
    private Long studentId;
    private List<Long> enrolledSections;
    private String message; // Mensaje de éxito o error
}
