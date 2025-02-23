package pe.estebancoder.solutions.student.enrollment.system.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentResponseDTO {
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

    /*
    En un futuro aplicare estos campos, algunos quizas se graben en StudentEntity y otros se calcularan en runtime cada vez que sea consultado:
    totalCreditsCompleted, currentGPA (Grade Point Average) y currentAcademicStatus => Enum AcademicStanding
     */
}
