package pe.estebancoder.solutions.student.enrollment.system.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomResponseDTO<T> {

    private T data;
    private LocalDateTime timestamp;
    private String status;
    private String uri;
}
