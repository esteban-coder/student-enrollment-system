package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import pe.estebancoder.solutions.student.enrollment.system.enums.EnrollmentDetailStatus;
import pe.estebancoder.solutions.student.enrollment.system.enums.EnrollmentStatus;
import pe.estebancoder.solutions.student.enrollment.system.enums.GradeStatus;

import java.time.LocalDateTime;

@Data
@Entity(name = "EnrollmentDetailEntity")
@Table(
        name = "TBL_ENROLLMENT_DETAIL",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_enrollment_section",
                        columnNames = {"ENROLLMENT_ID", "SECTION_ID"}
                )
        }
)
public class EnrollmentDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEnrollmentDetail")
    @SequenceGenerator(sequenceName = "SEQ_ENROLLMENT_DETAIL", allocationSize = 1, name = "seqEnrollmentDetail")
    @Column(name = "ENROLLMENT_DETAIL_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENROLLMENT_ID", nullable = false)
    private EnrollmentEntity enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECTION_ID", nullable = false)
    private SectionEntity section;

    @Column(name = "STATUS", nullable = false, length = 1)
    private String status;  // A: Active, W: Withdrawn, C: Completed

    @Column(name = "CREDITS", nullable = false)
    private Integer credits;

    @Column(name = "CREDITS_EARNED", nullable = false)
    private Integer creditsEarned;

    @Column(name = "FINAL_GRADE", precision = 4, scale = 2)
    private Double finalGrade;

    @Column(name = "GRADE_STATUS", length = 1)
    private String gradeStatus;  // A: Aprobado, D: Desaprobado, P: Pendiente

    @Column(name = "WITHDRAWAL_DATE")
    private LocalDateTime withdrawalDate;

    @Column(name = "WITHDRAWAL_REASON", length = 200)
    private String withdrawalReason;

    @PrePersist
    void setInitialStatus() {
        this.status = EnrollmentDetailStatus.ACTIVE.getCode();
        this.gradeStatus = GradeStatus.PENDING.getCode();
        this.creditsEarned = 0;
    }

}
