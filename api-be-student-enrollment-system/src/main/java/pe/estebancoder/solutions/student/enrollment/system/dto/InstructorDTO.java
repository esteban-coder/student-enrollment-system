package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InstructorDTO {

    private Long id;
    private String dni;
    private String fullName;
    private String email;
    private String phoneNumber;
    // private DepartmentDTO primaryDepartment;
    private String academicDegree;
    private LocalDate hireDate;
    private String status;
    // private List<SectionDTO> sections;
}
