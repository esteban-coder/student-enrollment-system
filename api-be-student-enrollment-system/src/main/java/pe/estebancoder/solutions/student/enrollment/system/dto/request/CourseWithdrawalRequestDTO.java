package pe.estebancoder.solutions.student.enrollment.system.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CourseWithdrawalRequestDTO {

    @NotNull(message = "El ID del detalle de matrícula es obligatorio")
    private Long enrollmentDetailId;

    @NotBlank(message = "La razón del retiro es obligatoria")
    @Size(min = 10, max = 200, message = "La razón del retiro debe tener entre 10 y 200 caracteres")
    private String withdrawalReason;
}
