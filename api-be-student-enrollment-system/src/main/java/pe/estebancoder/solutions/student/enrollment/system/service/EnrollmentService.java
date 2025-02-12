package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentResponseDTO;

public interface EnrollmentService {
    EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO request);
}
