package pe.estebancoder.solutions.student.enrollment.system.mapper;

import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.enums.EnrollmentDetailStatus;
import pe.estebancoder.solutions.student.enrollment.system.enums.EnrollmentStatus;
import pe.estebancoder.solutions.student.enrollment.system.enums.GradeStatus;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentInfoProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentInfoMapper {

    public static EnrollmentInfoDTO toDTO(EnrollmentInfoProjection projection) {
        EnrollmentInfoDTO dto = new EnrollmentInfoDTO();

        dto.setEnrollmentId(projection.getEnrollmentId());
        dto.setAcademicPeriod(projection.getAcademicPeriod());
        dto.setEnrollmentStatus(mapEnrollmentStatus(projection.getEnrollStatus()));
        dto.setTotalCredits(projection.getTotalCredits());
        dto.setEnrollmentDate(projection.getEnrollmentDate());

        // Student mapping
        dto.setStudentCode(projection.getStudentCode());
        dto.setStudentFullName(projection.getStudentFullname());

        // Enrollment Detail mapping
        dto.setEnrollmentDetailId(projection.getEnrollmentDetailId());
        dto.setGradeStatus(mapGradeStatus(projection.getGradeStatus()));
        dto.setDetailStatus(mapDetailStatus(projection.getEnrollDetailStatus()));

        // Section mapping
        dto.setSectionCode(projection.getSectionCode());
        dto.setSchedule(projection.getSchedule());
        dto.setRoomNumber(projection.getRoomNumber());

        // Course mapping
        dto.setCourseName(projection.getCourseName());
        dto.setCourseCode(projection.getCourseCode());
        dto.setCourseCredits(projection.getCourseCredits());

        // Instructor mapping
        dto.setInstructorFullName(projection.getInstructorFullname());

        return dto;
    }

    public static List<EnrollmentInfoDTO> toDTOList(List<EnrollmentInfoProjection> projections) {
        return projections.stream()
                .map(EnrollmentInfoMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static String mapEnrollmentStatus(String code) {
        try {
            return EnrollmentStatus.fromCode(code).name();
        } catch (IllegalArgumentException e) {
            return "UNKNOWN";
        }
    }

    private static String mapDetailStatus(String code) {
        try {
            return EnrollmentDetailStatus.fromCode(code).name();
        } catch (IllegalArgumentException e) {
            return "UNKNOWN";
        }
    }

    private static String mapGradeStatus(String code) {
        try {
            return GradeStatus.fromCode(code).name();
        } catch (IllegalArgumentException e) {
            return "UNKNOWN";
        }
    }
}
