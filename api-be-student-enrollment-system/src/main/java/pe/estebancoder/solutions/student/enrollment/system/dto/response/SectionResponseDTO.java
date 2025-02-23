package pe.estebancoder.solutions.student.enrollment.system.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SectionResponseDTO {
    private Long id;
    private String courseCode;
    private String courseName;
    private Integer courseCredits;
    private String departmentName;
    private String sectionCode;
    private String instructorName;
    private String academicPeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private String schedule;
    private String roomNumber;
    private int maxCapacity;
    private int enrolledStudentCount;
    private String status;

    // El campo calculado se obtiene automáticamente al serializar
    public int getRemainingCapacity() {
        return maxCapacity - enrolledStudentCount;
    }
}
