package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDTO {
    private Long id;
    private String studentCode;
    private String dni;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String gender;
    private LocalDate admissionDate;
    private String status;

}
