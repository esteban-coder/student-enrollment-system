package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "StudentEntity")
@Table(name = "TBL_STUDENT" /*, schema = "ENROLLMENT"*/)
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqStudent")
    @SequenceGenerator(sequenceName = "SEQ_STUDENT", allocationSize = 1, name = "seqStudent")
    @Column(name ="STUDENT_ID", nullable = false)
    private Long id;

    @Column(name ="NAME", nullable = false)
    private String name;

    @Column(name ="AGE", nullable = false)
    private int age;

/**/
    @OneToMany(mappedBy = "student")
    private List<EnrollmentEntity> enrollments;
/**/

}
