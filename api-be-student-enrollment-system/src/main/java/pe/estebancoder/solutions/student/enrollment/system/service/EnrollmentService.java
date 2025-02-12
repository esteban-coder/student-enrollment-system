package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentRequestDto;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentResponseDto;

public interface EnrollmentService {
    EnrollmentResponseDto enrollStudent(EnrollmentRequestDto request);
}
