package pe.estebancoder.solutions.student.enrollment.system.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import pe.estebancoder.solutions.student.enrollment.system.entity.SectionEntity;

@Data
public class EnrollmentDetailRequestDTO {

    @NotNull(message = "El ID de la sección es obligatorio")
    private Long sectionId;
}
