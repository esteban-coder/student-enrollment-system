package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TBL_STUDENT_CODE_SEQUENCE")
public class StudentCodeSequenceEntity {
    @Id
    @Column(name = "YEAR", nullable = false)
    private Integer year;

    @Column(name = "CURRENT_SEQUENCE", nullable = false)
    private Integer currentSequence;

    @Version  // Para manejo de concurrencia
    private Long version;
}
