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
    private String gender; // en caso se entre en modo edicion, el codigo ayuda en seleccionar el genero del combobox
    private String genderText;
    private LocalDate admissionDate;
    private String statusText; // mostrar el Texto no el codigo, el status no se modifica desde el FrontEnd sino desde la logica del BackEnd

    /*
    En un futuro aplicare estos campos, algunos quizas se graben en StudentEntity y otros se calcularan en runtime cada vez que sea consultado:
    totalCreditsCompleted, currentGPA (Grade Point Average) y currentAcademicStatus => Enum AcademicStanding
     */
}
