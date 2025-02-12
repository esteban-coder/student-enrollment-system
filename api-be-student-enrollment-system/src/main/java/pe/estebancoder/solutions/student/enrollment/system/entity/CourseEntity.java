package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "CourseEntity")
@Table(name = "TBL_COURSE" /*, schema = "ENROLLMENT"*/)
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCourse")
    @SequenceGenerator(sequenceName = "SEQ_COURSE", allocationSize = 1, name = "seqCourse")
    @Column(name ="COURSE_ID", nullable = false)
    private Long id;

    @Column(name ="NAME", nullable = false)
    private String name;

    @Column(name ="CREDITS", nullable = false)
    private int credits;
}
