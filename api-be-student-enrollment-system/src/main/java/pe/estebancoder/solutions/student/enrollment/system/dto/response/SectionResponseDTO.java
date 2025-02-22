package pe.estebancoder.solutions.student.enrollment.system.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SectionResponseDTO {
    private Long id;
    private String courseName;
    private String sectionCode;
    private String instructorName;
    private String academicPeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private String schedule;
    private String roomNumber;
    private int maxCapacity;
    private int currentEnrollment;
    private String status;

}
