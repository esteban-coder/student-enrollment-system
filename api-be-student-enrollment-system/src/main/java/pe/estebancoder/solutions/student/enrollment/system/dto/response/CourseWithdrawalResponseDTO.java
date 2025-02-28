package pe.estebancoder.solutions.student.enrollment.system.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseWithdrawalResponseDTO {

    private Long enrollmentDetailId;
    // private String academicPeriod;
    // private String enrollmentStatus;
    private String courseCode;
    private String courseName;
    private String sectionCode;
    private Integer credits;
    private String previousStatus;
    private String currentStatus;
    private LocalDateTime withdrawalDate;
    private String withdrawalReason;

    /*
    La lógica en el servicio se encargaría de:
    1. Validar que el retiro sea dentro del plazo permitido
    2. Actualizar el estado del detalle a WITHDRAWN
    3. Recalcular los créditos totales de la matrícula
    4. Actualizar el estado de la matrícula si es necesario
    5. Registrar la fecha y razón del retiro
    */
}
