package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentProjection> getAll();
    EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO request);
}
