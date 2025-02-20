package pe.estebancoder.solutions.student.enrollment.system.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentResponseDTO;
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

    public List<EnrollmentProjection> getAll(){
        return enrollmentRepository.getAllEnrollments();
    }

    @Transactional
    @Override
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO request) {
        StudentEntity student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Obtener matrículas existentes (una sola consulta)
        List<EnrollmentEntity> existingEnrollments = enrollmentRepository.findByStudentId(student.getId());
        List<Long> enrolledSections = new ArrayList<>();

        // Lista para almacenar los horarios de las nuevas secciones
        List<String> newSectionSchedules = new ArrayList<>();

        for (Long sectionId : request.getSectionIds()) {
            SectionEntity section = sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new RuntimeException("Section not found"));

            // Validar si la sección está llena (solo una vez por sección)
            if (section.getEnrollments().size() >= section.getMaxCapacity()) {
                throw new RuntimeException("Section is full");
            }

            // Validar matrícula duplicada y cruces de horario con matrículas existentes
            for (EnrollmentEntity enrollment : existingEnrollments) {
                if (enrollment.getSection().getCourse().getId().equals(section.getCourse().getId())) {
                    throw new RuntimeException("Student is already enrolled in this course");
                }

                if (enrollment.getSection().getSchedule().equals(section.getSchedule())) {
                    throw new RuntimeException("Schedule conflict detected with existing enrollment");
                }
            }

            // Validar cruces de horario entre las nuevas secciones
            if (newSectionSchedules.contains(section.getSchedule())) {
                throw new RuntimeException("Schedule conflict detected between new sections");
            }
            newSectionSchedules.add(section.getSchedule());

            // Crear matrícula
            EnrollmentEntity enrollment = new EnrollmentEntity();
            enrollment.setStudent(student);
            enrollment.setSection(section);
            enrollment.setType("Student");
            enrollmentRepository.save(enrollment);
            enrolledSections.add(sectionId);
        }

        // Crear respuesta fuera del loop
        EnrollmentResponseDTO response = new EnrollmentResponseDTO();
        response.setStudentId(student.getId());
        response.setEnrolledSections(enrolledSections);
        response.setMessage("Enrollment successful");
        return response;
    }
}

