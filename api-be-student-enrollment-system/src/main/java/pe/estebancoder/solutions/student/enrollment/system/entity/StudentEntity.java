package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import pe.estebancoder.solutions.student.enrollment.system.enums.StudentStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "StudentEntity")
@Table(name = "TBL_STUDENT")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqStudent")
    @SequenceGenerator(sequenceName = "SEQ_STUDENT", allocationSize = 1, name = "seqStudent")
    @Column(name = "STUDENT_ID", nullable = false)
    private Long id;

    @Column(name = "STUDENT_CODE", nullable = false, unique = true, length = 9)
    private String studentCode; // Ejemplo: 20000001A

    @Column(name = "DNI", nullable = false, unique = true, length = 8)
    private String dni;

    @Column(name = "FULLNAME", nullable = false, length = 100)
    private String fullName;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "PHONE_NUMBER", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "ADDRESS", nullable = false, length = 200)
    private String address;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    @Column(name = "GENDER", nullable = false, length = 1)
    private String gender;

    @Column(name = "ADMISSION_DATE", nullable = false)
    private LocalDate admissionDate;

    @Column(name = "STATUS", nullable = false, length = 1)
    private String status; // ACTIVE, INACTIVE, GRADUATED, SUSPENDED

    @OneToMany(mappedBy = "student")
    private List<EnrollmentEntity> enrollments;

    @PrePersist
    void setInitialStatus() {
        this.status = StudentStatus.ACTIVE.getCode();
    }
}