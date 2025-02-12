package pe.estebancoder.solutions.student.enrollment.system.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentRequestDto;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentResponseDto;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.SectionEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentEntity;
import pe.estebancoder.solutions.student.enrollment.system.repository.EnrollmentRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.SectionRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.StudentRepository;
import pe.estebancoder.solutions.student.enrollment.system.service.EnrollmentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;

    private final SectionRepository sectionRepository;

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(StudentRepository studentRepository, SectionRepository sectionRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    @Override
    public EnrollmentResponseDto enrollStudent(EnrollmentRequestDto request) {
        EnrollmentResponseDto response = new EnrollmentResponseDto();
        List<Long> enrolledSections = new ArrayList<>();

        StudentEntity student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        for (Long sectionId : request.getSectionIds()) {
            SectionEntity section = sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new RuntimeException("Section not found"));

            // Validar si el alumno ya esta inscrito en el curso (dentro de alguna seccion)
            boolean alreadyEnrolled = enrollmentRepository.findByStudentId(student.getId())
                    .stream()
                    .anyMatch(enrollment -> enrollment.getSection().getCourse().getId().equals(section.getCourse().getId()));

            if (alreadyEnrolled) {
                throw new RuntimeException("Student is already enrolled in this course");
            }

            // Validar si la seccion esta llena
            if (section.getEnrollments().size() >= section.getMaxCapacity()) {
                throw new RuntimeException("Section is full");
            }

            // Verificar si hay cruce de horarios
            boolean scheduleConflict = student.getEnrollments().stream()
                    .anyMatch(e -> e.getSection().getSchedule().equals(section.getSchedule()));

            if (scheduleConflict) {
                throw new RuntimeException("Schedule conflict detected");
            }

            // Inscribir al alumno
            EnrollmentEntity enrollment = new EnrollmentEntity();
            enrollment.setStudent(student);
            enrollment.setSection(section);
            enrollment.setType("Student");

            enrollmentRepository.save(enrollment);
            enrolledSections.add(sectionId);
        }

        response.setStudentId(student.getId());
        response.setEnrolledSections(enrolledSections);
        response.setMessage("Enrollment successful");

        return response;
    }
}

