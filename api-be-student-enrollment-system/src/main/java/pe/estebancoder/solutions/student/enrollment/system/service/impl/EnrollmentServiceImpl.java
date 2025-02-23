package pe.estebancoder.solutions.student.enrollment.system.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentDetailRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentDetailResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentDetailEntity;
import pe.estebancoder.solutions.student.enrollment.system.enums.EnrollmentDetailStatus;
import pe.estebancoder.solutions.student.enrollment.system.repository.EnrollmentDetailRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.SectionEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentEntity;
import pe.estebancoder.solutions.student.enrollment.system.repository.EnrollmentRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.SectionRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.StudentRepository;
import pe.estebancoder.solutions.student.enrollment.system.service.EnrollmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;

    private final SectionRepository sectionRepository;

    private final EnrollmentRepository enrollmentRepository;

    //private final EnrollmentDetailRepository enrollmentDetailRepository;

    public EnrollmentServiceImpl(StudentRepository studentRepository, SectionRepository sectionRepository, EnrollmentRepository enrollmentRepository/*, EnrollmentDetailRepository enrollmentDetailRepository*/) {
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
        this.enrollmentRepository = enrollmentRepository;
        //this.enrollmentDetailRepository = enrollmentDetailRepository;
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
        Optional<EnrollmentEntity> existingEnrollment = enrollmentRepository.findByStudentId(student.getId());
        List<Long> enrolledSections = new ArrayList<>();

        // Lista para almacenar los horarios de las nuevas secciones
        List<String> newSectionSchedules = new ArrayList<>();

        // Crear matricula cabecera
        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setDetails(new ArrayList<>()); // Obligatorio si no se inicializa en el Entity con = new ArrayList<>()
        Integer totalCredits = 0;

        for (EnrollmentDetailRequestDTO enrollmentDetailRequestDTO : request.getEnrollmentDetails()) {
            Long sectionId = enrollmentDetailRequestDTO.getSectionId();

            SectionEntity section = sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new RuntimeException("Section not found"));

            // Validar si la sección está llena (solo una vez por sección)
            // Integer enrolledStudentCount = enrollmentDetailRepository.countBySection_IdAndStatusIsNot(sectionId, EnrollmentDetailStatus.WITHDRAWN.getCode());
            // if (enrolledStudentCount >= section.getMaxCapacity()) {
            // if (section.getEnrollmentDetails().size() >= section.getMaxCapacity()) {
            if(!sectionRepository.availableForEnrollment(sectionId)){
                throw new RuntimeException("Section is full");
            }

            // Validar matrícula duplicada y cruces de horario con matrículas existentes
            if(existingEnrollment.isPresent()) {
                for (EnrollmentDetailEntity enrollmentDetail : existingEnrollment.get().getDetails()) {

                    if (enrollmentDetail.getSection().getCourse().getId().equals(section.getCourse().getId())) {
                        throw new RuntimeException("Student is already enrolled in this course");
                    }

                    if (enrollmentDetail.getSection().getSchedule().equals(section.getSchedule())) {
                        throw new RuntimeException("Schedule conflict detected with existing enrollment");
                    }
                }
            }

            // Validar cruces de horario entre las nuevas secciones
            if (newSectionSchedules.contains(section.getSchedule())) {
                throw new RuntimeException("Schedule conflict detected between new sections");
            }
            newSectionSchedules.add(section.getSchedule());

            // Crear matrícula detalle
            EnrollmentDetailEntity enrollmentDetail = new EnrollmentDetailEntity();
            enrollmentDetail.setEnrollment(enrollment); // obligatorio de lo contrario sale el error: cannot insert NULL into ("ENROLLMENT"."TBL_ENROLLMENT_DETAIL"."ENROLLMENT_ID")
            enrollmentDetail.setSection(section);
            enrollmentDetail.setCredits(section.getCourse().getCredits());
            enrollment.getDetails().add(enrollmentDetail); // obligatorio de lo contrario solo graba en cabecera pero nada en detalle

            totalCredits += enrollmentDetail.getCredits();

            // Actualizar el contador de alumnos matriculados en dicha seccion
            sectionRepository.updateEnrolledStudentCount(section.getId());
        }

        enrollment.setStudent(student);
        enrollment.setAcademicPeriod("2025-1");
        enrollment.setTotalCredits(totalCredits);
        enrollmentRepository.save(enrollment);

        // Crear respuesta
        EnrollmentResponseDTO response = new EnrollmentResponseDTO();
        response.setId(enrollment.getId());
        response.setStudentId(student.getId());
        response.setStudentCode(student.getStudentCode());
        response.setStudentFullName(student.getFullName());
        response.setAcademicPeriod(enrollment.getAcademicPeriod());
        response.setTotalCredits(enrollment.getTotalCredits());
        response.setEnrollmentDate(enrollment.getEnrollmentDate());
        response.setStatus(enrollment.getStatus());

        List<EnrollmentDetailResponseDTO> details = new ArrayList<>();
        enrollment.getDetails().forEach(detail -> {
            EnrollmentDetailResponseDTO detailResponse = new EnrollmentDetailResponseDTO();
            detailResponse.setId(detail.getId());
            detailResponse.setSectionId(detail.getSection().getId());
            detailResponse.setCourseCode(detail.getSection().getCourse().getCourseCode());
            detailResponse.setCourseName(detail.getSection().getCourse().getName());
            detailResponse.setSectionCode(detail.getSection().getSectionCode());
            detailResponse.setSchedule(detail.getSection().getSchedule());
            detailResponse.setInstructorName(detail.getSection().getInstructor().getFullName());
            detailResponse.setCredits(detail.getSection().getCourse().getCredits());
            detailResponse.setStatus(detail.getStatus());
            details.add(detailResponse);
        });
        response.setDetails(details);

        return response;
    }
}

