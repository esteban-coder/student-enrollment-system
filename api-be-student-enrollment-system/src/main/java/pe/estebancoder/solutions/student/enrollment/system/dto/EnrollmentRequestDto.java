package pe.estebancoder.solutions.student.enrollment.system.dto;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class EnrollmentRequestDto {
    private Long studentId;
    private List<Long> sectionIds;
}
