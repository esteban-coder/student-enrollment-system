package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SectionDTO {

    private Long id;
    private CourseDTO course;
    private String sectionCode;
    private InstructorDTO instructor;
    private String academicPeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private String schedule;
    private String roomNumber;
    private int maxCapacity;
    private int enrolledStudentCount;
    private String status;
}
