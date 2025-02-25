package pe.estebancoder.solutions.student.enrollment.system.controller;

import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.CustomResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.EnrollmentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.service.EnrollmentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<EnrollmentInfoDTO>> getAllEnrollmentInfo(@Param("studentCode") String studentCode, @Param("academicPeriod") String academicPeriod) {
        List<EnrollmentInfoDTO> enrollments = enrollmentService.getAllEnrollmentInfo(studentCode, academicPeriod);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/enroll")
    public ResponseEntity<CustomResponseDTO<EnrollmentResponseDTO>> enrollStudent(@Valid @RequestBody EnrollmentRequestDTO request) {

        EnrollmentResponseDTO enrollmentDTO = enrollmentService.enrollStudent(request);
        //return ResponseEntity.ok(enrollmentDTO);
        CustomResponseDTO<EnrollmentResponseDTO> responseDTO = new CustomResponseDTO<>();
        responseDTO.setData(enrollmentDTO);
        responseDTO.setStatus(HttpStatus.CREATED.name());
        responseDTO.setTimestamp(LocalDateTime.now());
        responseDTO.setUri("/api/v1/enrollments/" + enrollmentDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<EnrollmentResponseDTO>> getAll() {
        return ResponseEntity.ok(enrollmentService.getAll());
    }




}
