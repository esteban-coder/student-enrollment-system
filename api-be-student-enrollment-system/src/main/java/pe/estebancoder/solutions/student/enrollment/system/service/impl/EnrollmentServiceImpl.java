package pe.estebancoder.solutions.student.enrollment.system.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentDetailRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentDetailEntity;
import pe.estebancoder.solutions.student.enrollment.system.mapper.EnrollmentInfoMapper;
import pe.estebancoder.solutions.student.enrollment.system.mapper.EnrollmentMapper;
import pe.estebancoder.solutions.student.enrollment.system.repository.EnrollmentDetailRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentDetailProjection;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentInfoProjection;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.SectionEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentEntity;
import pe.estebancoder.solutions.student.enrollment.system.repository.EnrollmentRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.SectionRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.StudentRepository;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;
import pe.estebancoder.solutions.student.enrollment.system.service.EnrollmentService;

import java.util.*;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;

    private final SectionRepository sectionRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final EnrollmentDetailRepository enrollmentDetailRepository;

    private final EnrollmentMapper mapper;

    public EnrollmentServiceImpl(StudentRepository studentRepository, SectionRepository sectionRepository, EnrollmentRepository enrollmentRepository, EnrollmentMapper mapper, EnrollmentDetailRepository enrollmentDetailRepository) {
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentDetailRepository = enrollmentDetailRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public EnrollmentDTO enrollStudent(EnrollmentRequestDTO request) {
        StudentEntity student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Crear matricula cabecera
        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setComments(request.getComments());

        Integer totalCredits = 0;
        Integer totalEnrolledCourses = 0;

        // Obtener matrículas existentes (una sola consulta)
        Optional<EnrollmentEntity> optExistingEnrollment = enrollmentRepository.findByStudentIdAndAcademicPeriod(student.getId(), "2024-1");
        if(optExistingEnrollment.isPresent()) {
            EnrollmentEntity existingEnrollment = optExistingEnrollment.get();
            totalCredits = existingEnrollment.getTotalCredits();
            totalEnrolledCourses = existingEnrollment.getTotalEnrolledCourses();
            enrollment = existingEnrollment;
            enrollment.setStudent(student); // No es necesario, pues al ejecutar despues "existingEnrollment.getStudent().getFullName()" no hace la query a TBL_STUDENT, JPA lo toma de la query anterior "studentRepository.findById"
            // System.out.println("Nombre estudiante: " + existingEnrollment.getStudent().getFullName());
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
            totalEnrolledCourses += 1;

            // Actualizar el contador de alumnos matriculados en dicha seccion
            sectionRepository.updateEnrolledStudentCount(section.getId());
        }

        enrollment.setTotalCredits(totalCredits);
        enrollment.setTotalEnrolledCourses(totalEnrolledCourses);
        enrollmentRepository.save(enrollment);

        // Crear respuesta
        // return mapper.toDTO(enrollment);
        return mapper.toDto(enrollment);
    }

    @Override
    public List<EnrollmentResponseDTO> findAll() {
        List<EnrollmentEntity> enrollments = enrollmentRepository.findAll();
        return mapper.toDTOList(enrollments);
    }

    @Override
    public EnrollmentResponseDTO findBy(String studentCode, String academicPeriod) {
        StudentEntity student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Optional<EnrollmentEntity> optEnrollment = enrollmentRepository.findByStudentIdAndAcademicPeriod(student.getId(), academicPeriod);
        if(optEnrollment.isEmpty()) {
            throw new RuntimeException("Enrollment not found");
        }

        return mapper.toDTO(optEnrollment.get());
    }

    @Override
    public List<EnrollmentResponseDTO> findAllHeaders(String studentCode) {
        if(studentCode == null || studentCode.trim().isEmpty()) {
            List<EnrollmentEntity> enrollments = enrollmentRepository.findAll();
            return mapper.toHeaderDTOList(enrollments);
        }
        studentCode = studentCode.trim();
        Optional<StudentEntity> studentOptional = studentRepository.findByStudentCode(studentCode);
        if(studentOptional.isEmpty()) {
            //throw new RuntimeException("Student not found");
            return List.of();
        }
        List<EnrollmentEntity> enrollments = enrollmentRepository.findAllByStudent_Id(studentOptional.get().getId());
        return mapper.toHeaderDTOList(enrollments);
    }

    @Override
    public EnrollmentDTO searchBy(String studentCode, String academicPeriod) {
        StudentEntity student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Optional<EnrollmentEntity> optEnrollment = enrollmentRepository.findByStudentIdAndAcademicPeriod(student.getId(), academicPeriod);
        if(optEnrollment.isEmpty()) {
            throw new RuntimeException("Enrollment not found");
        }

        return mapper.toDto(optEnrollment.get());
    }

    @Override
    public List<EnrollmentDTO> searchAllHeaders(String studentCode) {
        if(studentCode == null || studentCode.trim().isEmpty()) {
            List<EnrollmentEntity> enrollments = enrollmentRepository.findAll();
            return mapper.toHeaderDtoList(enrollments);
        }
        studentCode = studentCode.trim();
        Optional<StudentEntity> studentOptional = studentRepository.findByStudentCode(studentCode);
        if(studentOptional.isEmpty()) {
            //throw new RuntimeException("Student not found");
            return List.of();
        }
        List<EnrollmentEntity> enrollments = enrollmentRepository.findAllByStudent_Id(studentOptional.get().getId());
        return mapper.toHeaderDtoList(enrollments);
    }

    @Override
    public List<EnrollmentResponseDTO> getAllHeaders(String studentCode) {
        Long studentId;
        if(studentCode == null || studentCode.trim().isEmpty()) {
            studentId = null;
        }
        else {
            studentCode = studentCode.trim();
            Optional<StudentEntity> studentOptional = studentRepository.findByStudentCode(studentCode);
            if (studentOptional.isEmpty()) {
                //throw new RuntimeException("Student not found");
                return List.of();
            }
            else{
                studentId = studentOptional.get().getId();
            }
        }
        List<EnrollmentEntity> enrollments = enrollmentRepository.getAllByStudent_Id(studentId);
        return mapper.toHeaderDTOList(enrollments);
    }

    public List<EnrollmentInfoDTO> getAll(String studentCode, String academicPeriod){
        List<EnrollmentInfoProjection> projections = enrollmentRepository.getAll(studentCode, academicPeriod);
        return EnrollmentInfoMapper.toDTOList(projections);
    }

    public List<EnrollmentDTO> searchAll(String studentCode, String academicPeriod){
        List<EnrollmentEntity> entities = enrollmentRepository.searchAll(studentCode, academicPeriod);
        return mapper.toDtoList(entities);
    }

    @Override
    public EnrollmentResponseDTO getBy(String studentCode, String academicPeriod) {
        EnrollmentProjection enrollment = enrollmentRepository.getBy(studentCode, academicPeriod);
        if(enrollment == null) {
            throw new RuntimeException("Enrollment not found");
        }

        List<EnrollmentDetailProjection> enrollmentDetails = enrollmentDetailRepository.getEnrollmentDetail(enrollment.getEnrollmentId());

        return mapper.fromProjectionToDTO(enrollment, enrollmentDetails);
    }
}

