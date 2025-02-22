package pe.estebancoder.solutions.student.enrollment.system.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateStudentRequestDTO {
    private String dni;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String gender;
    private LocalDate admissionDate;

}
