package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "EnrollmentEntity")
@Table(name = "TBL_ENROLLMENT")
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEnrollment")
    @SequenceGenerator(sequenceName = "SEQ_ENROLLMENT", allocationSize = 1, name = "seqEnrollment")
    @Column(name = "ENROLLMENT_ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID", nullable = false)
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "SECTION_ID", nullable = false)
    private SectionEntity section;

    @Column(name = "ENROLLMENT_DATE", nullable = false)
    private LocalDateTime enrollmentDate;

    @Column(name = "STATUS", nullable = false, length = 1)
    private String status; // ENROLLED, WITHDRAWN, COMPLETED

    @Column(name = "FINAL_GRADE", nullable = true, precision = 4, scale = 2)
    private Double finalGrade;

    @PrePersist
    void setStatus() {
        this.status = "1";
        this.enrollmentDate = LocalDateTime.now();
    }
}
