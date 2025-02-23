package pe.estebancoder.solutions.student.enrollment.system.controller;

import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<EnrollmentInfoDTO>> getAllEnrollmentInfo() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollmentInfo(null, null));
    }

    @GetMapping("/{studentCode}")
    public ResponseEntity<List<EnrollmentInfoDTO>> getAllEnrollmentInfoByStudentCode(@PathVariable String studentCode) {
        return ResponseEntity.ok(enrollmentService.getAllEnrollmentInfo(studentCode, null));
    }

    @GetMapping("/{studentCode}/{academicPeriod}")
    public ResponseEntity<List<EnrollmentInfoDTO>> getAllEnrollmentInfoByStudentCodeAndAcademicPeriod(@PathVariable String studentCode, @PathVariable String academicPeriod) {
        return ResponseEntity.ok(enrollmentService.getAllEnrollmentInfo(studentCode, academicPeriod));
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
}
