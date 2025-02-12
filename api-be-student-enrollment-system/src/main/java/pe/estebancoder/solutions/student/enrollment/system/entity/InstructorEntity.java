package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "InstructorEntity")
@Table(name = "TBL_INSTRUCTOR")
public class InstructorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqInstructor")
    @SequenceGenerator(sequenceName = "SEQ_INSTRUCTOR", allocationSize = 1, name = "seqInstructor")
    @Column(name ="INSTRUCTOR_ID", nullable = false)
    private Long id;

    @Column(name ="NAME", nullable = false)
    private String name;

    @Column(name= "DEPARTMENT", nullable = false)
    private String department;

/*
    @OneToMany(mappedBy = "instructor")
    private List<SectionEntity> sections;
*/

}
