package pe.estebancoder.solutions.student.enrollment.system.mapper;

import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.enums.EnrollmentDetailStatus;
import pe.estebancoder.solutions.student.enrollment.system.enums.EnrollmentStatus;
import pe.estebancoder.solutions.student.enrollment.system.enums.GradeStatus;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentInfoProjection;

import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentInfoMapper {

    public static EnrollmentInfoDTO toDTO(EnrollmentInfoProjection projection) {
        EnrollmentInfoDTO dto = new EnrollmentInfoDTO();

        dto.setEnrollmentId(projection.getEnrollmentId());
        dto.setAcademicPeriod(projection.getAcademicPeriod());
        dto.setTotalCredits(projection.getTotalCredits());
        dto.setTotalEnrolledCourses(projection.getTotalEnrolledCourses());
        dto.setComments(projection.getComments());
        dto.setEnrollmentDate(projection.getEnrollmentDate());
        dto.setEnrollmentStatus(mapEnrollmentStatus(projection.getEnrollStatus()));

        // Student mapping
        dto.setStudentCode(projection.getStudentCode());
        dto.setStudentFullName(projection.getStudentFullname());

        // Enrollment Detail mapping
        dto.setEnrollmentDetailId(projection.getEnrollmentDetailId());
        dto.setDetailStatus(mapDetailStatus(projection.getEnrollDetailStatus()));
        dto.setGradeStatus(mapGradeStatus(projection.getGradeStatus()));

        // Section mapping
        dto.setSectionCode(projection.getSectionCode());
        dto.setSchedule(projection.getSchedule());
        dto.setRoomNumber(projection.getRoomNumber());

        // Course mapping
        dto.setCourseCode(projection.getCourseCode());
        dto.setCourseName(projection.getCourseName());
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

    public static String mapEnrollmentStatus(String code) {
        try {
            return EnrollmentStatus.fromCode(code).name();
        } catch (IllegalArgumentException e) {
            return "UNKNOWN";
        }
    }

    public static String mapEnrollmentDetailStatus(String code) {
        try {
            return EnrollmentDetailStatus.fromCode(code).name();
        } catch (IllegalArgumentException e) {
            return "UNKNOWN";
        }
    }

}
