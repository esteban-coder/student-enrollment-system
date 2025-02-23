package pe.estebancoder.solutions.student.enrollment.system.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateSectionRequestDTO {
    @NotNull(message = "El ID del curso es obligatorio")
    private Long courseId;

    @NotBlank(message = "El código de sección es obligatorio")
    @Pattern(regexp = "[A-Z]", message = "El código de sección debe ser una letra mayúscula")
    private String sectionCode; // A, B, C, etc.

    @NotNull(message = "El ID del instructor es obligatorio")
    private Long instructorId;

    @NotBlank(message = "El periodo académico es obligatorio")
    @Pattern(regexp = "\\d{4}-[1-2]", message = "El periodo académico debe tener el formato YYYY-N donde N es 1 o 2")
    private String academicPeriod; // Ejemplo: 2024-1

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede ser pasada")
    private LocalDate startDate;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser futura")
    private LocalDate endDate;

    @NotBlank(message = "El horario es obligatorio")
    @Size(max = 100, message = "El horario no debe exceder los 100 caracteres")
    private String schedule;

    @NotBlank(message = "El número de aula es obligatorio")
    @Size(max = 10, message = "El número de aula no debe exceder los 10 caracteres")
    private String roomNumber;

    @NotNull(message = "La capacidad máxima es obligatoria")
    @Min(value = 5, message = "La capacidad mínima debe ser 5 estudiantes")
    @Max(value = 50, message = "La capacidad máxima debe ser 50 estudiantes")
    private int maxCapacity;

    @AssertTrue(message = "La fecha de fin debe ser posterior a la fecha de inicio")
    private boolean isDateRangeValid() {
        if (startDate == null || endDate == null) {
            return true; // las validaciones @NotNull se encargarán de esto
        }
        return endDate.isAfter(startDate);
    }
}
