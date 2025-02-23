package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import pe.estebancoder.solutions.student.enrollment.system.enums.SectionStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "SectionEntity")
@Table(name = "TBL_SECTION")
public class SectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSection")
    @SequenceGenerator(sequenceName = "SEQ_SECTION", allocationSize = 1, name = "seqSection")
    @Column(name = "SECTION_ID", nullable = false)
    private Long id;

    //FETCH.EAGER por defecto, lo mantenemos asi, para que se realice el join y traiga la data, en lugar de FETCH.LAZY que realizaria una consulta select separada despues cuando se necesite
    // @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "COURSE_ID", nullable = false)
    private CourseEntity course;

    @Column(name = "SECTION_CODE", nullable = false, length = 2)
    private String sectionCode; // A, B, C, etc.

    @ManyToOne
    @JoinColumn(name = "INSTRUCTOR_ID", nullable = false)
    private InstructorEntity instructor;

    @Column(name = "ACADEMIC_PERIOD", nullable = false, length = 6)
    private String academicPeriod; // Ejemplo: 2024-1

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "SCHEDULE", nullable = false, length = 100)
    private String schedule;

    @Column(name = "ROOM_NUMBER", nullable = false, length = 10)
    private String roomNumber;

    @Column(name = "MAX_CAPACITY", nullable = false)
    private int maxCapacity;

    @Column(name = "ENROLLED_STUDENT_COUNT", nullable = false)
    private int enrolledStudentCount;

    @Column(name = "STATUS", nullable = false, length = 1)
    private String status; // OPEN, CLOSED, CANCELLED, IN_PROGRESS, COMPLETED

    // Comentado pues no vamos a usarlo por ejemplo: if (section.getEnrollmentDetails().size() >= section.getMaxCapacity()) { ... }, vamos a usar una query directa, en lugar de traernos en memoria toda la data de details
/*
    @OneToMany(mappedBy = "section")
    private List<EnrollmentDetailEntity> enrollmentDetails;
*/

    @PrePersist
    void setInitialStatus() {
        this.status = SectionStatus.OPEN.getCode();
        this.enrolledStudentCount = 0;
    }
}
