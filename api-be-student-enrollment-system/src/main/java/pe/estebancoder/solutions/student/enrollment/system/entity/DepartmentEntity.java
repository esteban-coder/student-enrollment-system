package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "DepartmentEntity")
@Table(name = "TBL_DEPARTMENT")
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqDepartment")
    @SequenceGenerator(sequenceName = "SEQ_DEPARTMENT", allocationSize = 1, name = "seqDepartment")
    @Column(name = "DEPARTMENT_ID", nullable = false)
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true, length = 10)
    private String code; // Ejemplo: "CS", "MATH", "PHYS"

    @Column(name = "NAME", nullable = false, unique = true, length = 100)
    private String name; // Ejemplo: "Computer Science", "Mathematics", "Physics"

    @Column(name = "STATUS", nullable = false, length = 1)
    private String status; // // ACTIVE, INACTIVE

    @PrePersist
    void setStatus() {
        this.status = "1";
    }
}
