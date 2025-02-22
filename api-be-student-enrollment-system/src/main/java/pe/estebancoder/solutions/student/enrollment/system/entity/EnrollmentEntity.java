package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import pe.estebancoder.solutions.student.enrollment.system.enums.EnrollmentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "EnrollmentEntity")
@Table(
        name = "TBL_ENROLLMENT",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_enrollment_student_period",
                        columnNames = {"STUDENT_ID", "ACADEMIC_PERIOD"}
                )
        }
)
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEnrollment")
    @SequenceGenerator(sequenceName = "SEQ_ENROLLMENT", allocationSize = 1, name = "seqEnrollment")
    @Column(name = "ENROLLMENT_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID", nullable = false)
    private StudentEntity student;

    @Column(name = "ACADEMIC_PERIOD", nullable = false, length = 6)
    private String academicPeriod;  // Format: YYYY-N (ejemplo: 2024-1)

    @Column(name = "TOTAL_CREDITS", nullable = false)
    private Integer totalCredits;

    @Column(name = "TOTAL_CREDITS_EARNED", nullable = false)
    private Integer totalCreditsEarned;

    @Column(name = "WEIGHTED_AVERAGE", precision = 4, scale = 2)
    private Double weightedAverage;

    @Column(name = "STATUS", nullable = false, length = 10)
    private String status;  // ENROLLED, WITHDRAWN, COMPLETED

    @Column(name = "ENROLLMENT_DATE", nullable = false)
    private LocalDateTime enrollmentDate;

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrollmentDetailEntity> details;

    // Implementación a futuro
    /*
    @Column(name = "TOTAL_AMOUNT", nullable = false, precision = 10, scale = 2)
    private Double totalAmount;

    @Column(name = "PAYMENT_TYPE", nullable = false, length = 2)
    private String paymentType;  // CC: Credit Card, CA: Cash, etc.

    @Column(name = "PAYMENT_STATUS", nullable = false, length = 1)
    private String paymentStatus;  // P: Pending, C: Completed
    */

    @PrePersist
    void setInitialStatus() {
        this.status = EnrollmentStatus.ENROLLED.getCode();
        this.enrollmentDate = LocalDateTime.now();
        this.totalCreditsEarned = 0;
    }
}
