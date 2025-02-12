package pe.estebancoder.solutions.student.enrollment.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentRequestDto;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentResponseDto;
import pe.estebancoder.solutions.student.enrollment.system.service.EnrollmentService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentResponseDto> enrollStudent(@RequestBody EnrollmentRequestDto request) {
        try {
            EnrollmentResponseDto response = enrollmentService.enrollStudent(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception ex){
            EnrollmentResponseDto response = new EnrollmentResponseDto();
            response.setStudentId(request.getStudentId());
            response.setEnrolledSections(new ArrayList<>());
            response.setMessage(ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
