package pe.estebancoder.solutions.student.enrollment.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.CustomResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.service.EnrollmentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentProjection>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAll());
    }

    @PostMapping("/enroll")
    public ResponseEntity<CustomResponseDTO<EnrollmentResponseDTO>> enrollStudent(@RequestBody EnrollmentRequestDTO request) {
        EnrollmentResponseDTO enrollmentDTO = enrollmentService.enrollStudent(request);
        //return ResponseEntity.ok(enrollmentDTO);
        CustomResponseDTO<EnrollmentResponseDTO> responseDTO = new CustomResponseDTO<>();
        responseDTO.setData(enrollmentDTO);
        responseDTO.setStatus(HttpStatus.CREATED.name());
        responseDTO.setTimestamp(LocalDateTime.now());
        responseDTO.setUri("/api/v1/enrollments/" + enrollmentDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
