package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO enrollStudent(EnrollmentRequestDTO request);
    List<EnrollmentResponseDTO> findAll();
    EnrollmentResponseDTO findBy(String studentCode, String academicPeriod);
    List<EnrollmentResponseDTO> findAllHeaders(String studentCode);
    EnrollmentDTO searchBy(String studentCode, String academicPeriod);
    List<EnrollmentDTO> searchAllHeaders(String studentCode);
    List<EnrollmentResponseDTO> getAllHeaders(String studentCode);
    List<EnrollmentInfoDTO> getAll(String studentCode, String academicPeriod);
    List<EnrollmentDTO> searchAll(String studentCode, String academicPeriod);
    EnrollmentResponseDTO getBy(String studentCode, String academicPeriod);


}
