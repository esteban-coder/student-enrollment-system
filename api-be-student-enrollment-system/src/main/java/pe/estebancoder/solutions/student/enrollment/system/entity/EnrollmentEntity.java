package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    @Column(name = "ENROLLMENT_DATE", nullable = false)
    private LocalDateTime enrollmentDate;

    @Column(name = "TYPE", nullable = false)
    private String type; // STUDENT, AUDIT

    @Column(name ="STATUS", nullable = true)
    private String status; // ENROLLED, WITHDRAWN, COMPLETED

    @PrePersist
    void setStatus() {
        this.status="1";
    }
}
