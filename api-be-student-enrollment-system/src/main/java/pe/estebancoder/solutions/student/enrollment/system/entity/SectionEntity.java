package pe.estebancoder.solutions.student.enrollment.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "SectionEntity")
@Table(name = "TBL_SECTION" /*, schema = "ENROLLMENT"*/)
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSection")
    @SequenceGenerator(sequenceName = "SEQ_SECTION", allocationSize = 1, name = "seqSection")
    @Column(name ="SECTION_ID", nullable = false)
    private Long id;

    @Column(name ="SCHEDULE", nullable = false)
    private String schedule;

    @Column(name ="MAXCAPACITY", nullable = false)
    private int maxCapacity;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private CourseEntity course;

    @OneToMany(mappedBy = "section")
    private List<EnrollmentEntity> enrollments;
}
