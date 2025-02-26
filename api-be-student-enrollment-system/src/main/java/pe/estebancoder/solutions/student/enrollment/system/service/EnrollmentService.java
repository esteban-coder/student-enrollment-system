package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO request);
    List<EnrollmentResponseDTO> findAll();
    EnrollmentResponseDTO findBy(String studentCode, String academicPeriod);
    List<EnrollmentInfoDTO> getAll(String studentCode, String academicPeriod);
    EnrollmentResponseDTO getBy(String studentCode, String academicPeriod);


}
