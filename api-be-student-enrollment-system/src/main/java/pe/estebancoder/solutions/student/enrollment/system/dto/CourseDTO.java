package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

@Data
public class CourseDTO {

    private Long id;
    private String courseCode;
    private String name;
    private String description;
    private int credits;
    // private DepartmentDTO department;
    private String prerequisites;
    private String status;
    // private List<SectionDTO> sections;
}
