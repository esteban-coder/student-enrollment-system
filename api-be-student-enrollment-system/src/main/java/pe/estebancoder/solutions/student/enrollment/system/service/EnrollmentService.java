package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentProjection> getAll();
    EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO request);
}
