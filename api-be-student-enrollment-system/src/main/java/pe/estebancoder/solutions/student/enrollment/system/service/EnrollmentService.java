package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentInfoDTO> getAllEnrollmentInfo(String studentCode);
    EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO request);
}
