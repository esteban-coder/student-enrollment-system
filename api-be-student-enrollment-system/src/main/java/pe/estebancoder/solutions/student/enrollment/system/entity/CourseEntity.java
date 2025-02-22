package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import pe.estebancoder.solutions.student.enrollment.system.enums.CourseStatus;

import java.util.List;

@Data
@Entity(name = "CourseEntity")
@Table(name = "TBL_COURSE")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCourse")
    @SequenceGenerator(sequenceName = "SEQ_COURSE", allocationSize = 1, name = "seqCourse")
    @Column(name = "COURSE_ID", nullable = false)
    private Long id;

    @Column(name = "COURSE_CODE", nullable = false, unique = true, length = 10)
    private String courseCode; // Ejemplo: MA113

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true, length = 500)
    private String description;

    @Column(name = "CREDITS", nullable = false)
    private int credits;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private DepartmentEntity department;

    @Column(name = "PREREQUISITES", nullable = true, length = 200)
    private String prerequisites; // Lista de códigos de cursos prerequisitos

    @Column(name = "STATUS", nullable = false, length = 1)
    private String status; // ACTIVE, INACTIVE, DISCONTINUED

    @OneToMany(mappedBy = "course")
    private List<SectionEntity> sections;

    @PrePersist
    void setInitialStatus() {
        this.status = CourseStatus.ACTIVE.getCode();
    }
}
