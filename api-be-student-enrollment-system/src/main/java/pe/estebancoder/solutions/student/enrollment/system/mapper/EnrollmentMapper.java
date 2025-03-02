package pe.estebancoder.solutions.student.enrollment.system.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pe.estebancoder.solutions.student.enrollment.system.dto.StudentDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentDetailResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.EnrollmentEntity;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentDetailProjection;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnrollmentMapper {

    private final ModelMapper modelMapper;

    public EnrollmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EnrollmentDTO toDto(EnrollmentEntity entity) {
        return modelMapper.map(entity, EnrollmentDTO.class);
    }

    public List<EnrollmentDTO> toDtoList(List<EnrollmentEntity> enrollments) {
        return enrollments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EnrollmentDTO toHeaderDto(EnrollmentEntity entity) {
        if (entity == null) {
            return null;
        }

        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(entity.getId());
        dto.setAcademicPeriod(entity.getAcademicPeriod());
        dto.setTotalCredits(entity.getTotalCredits());
        dto.setTotalEnrolledCourses(entity.getTotalEnrolledCourses());
        dto.setComments(entity.getComments());
        dto.setEnrollmentDate(entity.getEnrollmentDate());
        dto.setStatus(entity.getStatus());
        dto.setTotalCreditsEarned(entity.getTotalCreditsEarned());
        dto.setWeightedAverage(entity.getWeightedAverage());

        // Mapear el Student utilizando ModelMapper o manualmente
        if (entity.getStudent() != null) {
            dto.setStudent(modelMapper.map(entity.getStudent(), StudentDTO.class));
        }

        // No se mapea el campo 'details' para este caso específico

        return dto;
    }

    public List<EnrollmentDTO> toHeaderDtoList(List<EnrollmentEntity> enrollments) {
        return enrollments.stream()
                .map(this::toHeaderDto)
                .collect(Collectors.toList());
    }

    public EnrollmentResponseDTO toDTO(EnrollmentEntity enrollment) {

        EnrollmentResponseDTO responseDTO = new EnrollmentResponseDTO();
        responseDTO.setId(enrollment.getId());
        // responseDTO.setStudentId(enrollment.getStudent().getId());
        responseDTO.setStudentCode(enrollment.getStudent().getStudentCode());
        responseDTO.setStudentFullName(enrollment.getStudent().getFullName());
        responseDTO.setAcademicPeriod(enrollment.getAcademicPeriod());
        responseDTO.setTotalCredits(enrollment.getTotalCredits());
        responseDTO.setTotalEnrolledCourses(enrollment.getTotalEnrolledCourses());
        responseDTO.setComments(enrollment.getComments());
        responseDTO.setEnrollmentDate(enrollment.getEnrollmentDate());
        // responseDTO.setStatus(enrollment.getStatus());
        responseDTO.setStatus(EnrollmentInfoMapper.mapEnrollmentStatus(enrollment.getStatus()));

        List<EnrollmentDetailResponseDTO> details = new ArrayList<>();
        enrollment.getDetails().forEach(detail -> {
            EnrollmentDetailResponseDTO detailResponseDTO = new EnrollmentDetailResponseDTO();
            detailResponseDTO.setId(detail.getId());
            // detailResponseDTO.setSectionId(detail.getSection().getId());
            detailResponseDTO.setCourseCode(detail.getSection().getCourse().getCourseCode());
            detailResponseDTO.setCourseName(detail.getSection().getCourse().getName());
            detailResponseDTO.setSectionCode(detail.getSection().getSectionCode());
            detailResponseDTO.setSchedule(detail.getSection().getSchedule());
            detailResponseDTO.setInstructorName(detail.getSection().getInstructor().getFullName());
            detailResponseDTO.setCredits(detail.getSection().getCourse().getCredits());
            // detailResponseDTO.setStatus(detail.getStatus());
            detailResponseDTO.setStatus(EnrollmentInfoMapper.mapEnrollmentDetailStatus(detail.getStatus()));
            details.add(detailResponseDTO);
        });
        responseDTO.setDetails(details);
        return responseDTO;
    }

    public EnrollmentResponseDTO toHeaderDTO(EnrollmentEntity enrollment) {

        EnrollmentResponseDTO responseDTO = new EnrollmentResponseDTO();
        responseDTO.setId(enrollment.getId());
        // responseDTO.setStudentId(enrollment.getStudent().getId());
        responseDTO.setStudentCode(enrollment.getStudent().getStudentCode());
        responseDTO.setStudentFullName(enrollment.getStudent().getFullName());
        responseDTO.setAcademicPeriod(enrollment.getAcademicPeriod());
        responseDTO.setTotalCredits(enrollment.getTotalCredits());
        responseDTO.setTotalEnrolledCourses(enrollment.getTotalEnrolledCourses());
        responseDTO.setComments(enrollment.getComments());
        responseDTO.setEnrollmentDate(enrollment.getEnrollmentDate());
        // responseDTO.setStatus(enrollment.getStatus());
        responseDTO.setStatus(EnrollmentInfoMapper.mapEnrollmentStatus(enrollment.getStatus()));

        return responseDTO;
    }

    public List<EnrollmentResponseDTO> toDTOList(List<EnrollmentEntity> enrollments) {
        return enrollments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EnrollmentResponseDTO> toHeaderDTOList(List<EnrollmentEntity> enrollments) {
        return enrollments.stream()
                .map(this::toHeaderDTO)
                .collect(Collectors.toList());
    }

    public EnrollmentResponseDTO fromProjectionToDTO(EnrollmentProjection enrollment, List<EnrollmentDetailProjection> enrollmentDetails) {

        EnrollmentResponseDTO responseDTO = new EnrollmentResponseDTO();
        responseDTO.setId(enrollment.getEnrollmentId());
        responseDTO.setStudentCode(enrollment.getStudentCode());
        responseDTO.setStudentFullName(enrollment.getStudentFullname());
        responseDTO.setAcademicPeriod(enrollment.getAcademicPeriod());
        responseDTO.setTotalCredits(enrollment.getTotalCredits());
        responseDTO.setTotalEnrolledCourses(enrollment.getTotalEnrolledCourses());
        responseDTO.setComments(enrollment.getComments());
        responseDTO.setEnrollmentDate(enrollment.getEnrollmentDate());
        // responseDTO.setStatus(enrollment.getStatus());
        responseDTO.setStatus(EnrollmentInfoMapper.mapEnrollmentStatus(enrollment.getEnrollStatus()));

        List<EnrollmentDetailResponseDTO> details = new ArrayList<>();
        enrollmentDetails.forEach(detail -> {
            EnrollmentDetailResponseDTO detailResponseDTO = new EnrollmentDetailResponseDTO();
            detailResponseDTO.setId(detail.getEnrollmentDetailId());
            detailResponseDTO.setCourseCode(detail.getCourseCode());
            detailResponseDTO.setCourseName(detail.getCourseName());
            detailResponseDTO.setSectionCode(detail.getSectionCode());
            detailResponseDTO.setSchedule(detail.getSchedule());
            detailResponseDTO.setInstructorName(detail.getInstructorFullname());
            detailResponseDTO.setCredits(detail.getCourseCredits());
            // detailResponseDTO.setStatus(detail.getEnrollDetailStatus());
            detailResponseDTO.setStatus(EnrollmentInfoMapper.mapEnrollmentDetailStatus(detail.getEnrollDetailStatus()));
            details.add(detailResponseDTO);
        });
        responseDTO.setDetails(details);
        return responseDTO;
    }

}
