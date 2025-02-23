package pe.estebancoder.solutions.student.enrollment.system.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentDetailRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentDetailResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentDetailEntity;
import pe.estebancoder.solutions.student.enrollment.system.mapper.EnrollmentInfoMapper;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentInfoProjection;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.SectionEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentEntity;
import pe.estebancoder.solutions.student.enrollment.system.repository.EnrollmentRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.SectionRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.StudentRepository;
import pe.estebancoder.solutions.student.enrollment.system.service.EnrollmentService;

import java.util.*;

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

    public List<EnrollmentInfoDTO> getAllEnrollmentInfo(String studentCode, String academicPeriod){
        List<EnrollmentInfoProjection> projections = enrollmentRepository.getAllEnrollmentInfo(studentCode, academicPeriod);
        return EnrollmentInfoMapper.toDTOList(projections);
    }

    @Transactional
    @Override
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO request) {
        StudentEntity student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Crear matricula cabecera
        EnrollmentEntity enrollment = new EnrollmentEntity();
        Integer totalCredits = 0;

        // Obtener matrículas existentes (una sola consulta)
        Optional<EnrollmentEntity> optExistingEnrollment = enrollmentRepository.findByStudentIdAndAcademicPeriod(student.getId(), "2024-1");
        if(optExistingEnrollment.isPresent()) {
            EnrollmentEntity existingEnrollment = optExistingEnrollment.get();
            totalCredits = existingEnrollment.getTotalCredits();
            enrollment = existingEnrollment;
        }
        else{
            enrollment.setDetails(new ArrayList<>()); // Obligatorio si no se inicializa en el Entity con = new ArrayList<>() //no se pone arriba, se comprueba que si se creó sin details, al consulta, viene una lista vacia
            enrollment.setStudent(student);
            enrollment.setAcademicPeriod("2024-1");
        }

        // Lista para almacenar los horarios de las nuevas secciones
        List<String> newSectionSchedules = new ArrayList<>();
        // Lista para almacenar los IDs de cursos de las nuevas secciones
        Set<Long> newCoursesIds = new HashSet<>();

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
            if(optExistingEnrollment.isPresent()) {
                for (EnrollmentDetailEntity enrollmentDetail : optExistingEnrollment.get().getDetails()) {

                    if (enrollmentDetail.getSection().getCourse().getId().equals(section.getCourse().getId())) {
                        throw new RuntimeException("Student is already enrolled in this course");
                    }

                    if (enrollmentDetail.getSection().getSchedule().equals(section.getSchedule())) {
                        throw new RuntimeException("Schedule conflict detected with existing enrollment");
                    }
                }
            }

            // Validar que no haya duplicidad de curso entre las nuevas secciones
            if (!newCoursesIds.add(section.getCourse().getId())) {
                throw new RuntimeException("Cannot enroll in multiple sections of the same course");
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
        // response.setStatus(enrollment.getStatus());
        response.setStatus(EnrollmentInfoMapper.mapEnrollmentStatus(enrollment.getStatus()));

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
            // detailResponse.setStatus(detail.getStatus());
            detailResponse.setStatus(EnrollmentInfoMapper.mapEnrollmentDetailStatus(detail.getStatus()));
            details.add(detailResponse);
        });
        response.setDetails(details);

        return response;
    }
}

