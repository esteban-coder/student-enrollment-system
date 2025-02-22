package pe.estebancoder.solutions.student.enrollment.system.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateSectionRequestDTO {
    private Long courseId;
    private String sectionCode; // A, B, C, etc.
    private Long instructorId;
    private String academicPeriod; // Ejemplo: 2024-1
    private LocalDate startDate;
    private LocalDate endDate;
    private String schedule;
    private String roomNumber;
    private int maxCapacity;

}
