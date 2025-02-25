package pe.estebancoder.solutions.student.enrollment.system.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class EnrollmentRequestDTO {

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long studentId;

    private String comments;

    @Valid
    @NotNull(message = "Debe seleccionar al menos una sección")
    @Size(min = 1, message = "Debe seleccionar al menos una sección")
    private List<EnrollmentDetailRequestDTO> enrollmentDetails;
}
