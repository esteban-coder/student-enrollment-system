package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "EnrollmentEntity")
@Table(name = "TBL_ENROLLMENT" /*, schema = "ENROLLMENT"*/)
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEnrollment")
    @SequenceGenerator(sequenceName = "SEQ_ENROLLMENT", allocationSize = 1, name = "seqEnrollment")
    @Column(name ="ENROLLMENT_ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "SECTION_ID")
    private SectionEntity section;

    private String type;
}
