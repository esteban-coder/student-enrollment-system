package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "InstructorEntity")
@Table(name = "TBL_INSTRUCTOR")
public class InstructorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqInstructor")
    @SequenceGenerator(sequenceName = "SEQ_INSTRUCTOR", allocationSize = 1, name = "seqInstructor")
    @Column(name = "INSTRUCTOR_ID", nullable = false)
    private Long id;

    @Column(name = "DNI", nullable = false, unique = true, length = 8)
    private String dni;

    @Column(name = "FULLNAME", nullable = false, length = 100)
    private String fullName;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "PHONE_NUMBER", nullable = false, length = 20)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "PRIMARY_DEPARTMENT_ID", nullable = false)
    private DepartmentEntity primaryDepartment;

    @Column(name = "ACADEMIC_DEGREE", nullable = false, length = 50)
    private String academicDegree;

    @Column(name = "HIRE_DATE", nullable = false)
    private LocalDate hireDate;

    @Column(name = "STATUS", nullable = false, length = 1)
    private String status; // ACTIVE, INACTIVE, ON_LEAVE

    @OneToMany(mappedBy = "instructor")
    private List<SectionEntity> sections;

    @PrePersist
    void setStatus() {
        this.status = "1";
    }
}
