package pe.estebancoder.solutions.student.enrollment.system.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentDTO;
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

    @PostMapping("/enroll")
    public ResponseEntity<CustomResponseDTO<EnrollmentDTO>> enrollStudent(@Valid @RequestBody EnrollmentRequestDTO request) {

        EnrollmentDTO enrollmentDTO = enrollmentService.enrollStudent(request);
        //return ResponseEntity.ok(enrollmentDTO);
        CustomResponseDTO<EnrollmentDTO> responseDTO = new CustomResponseDTO<>();
        responseDTO.setData(enrollmentDTO);
        responseDTO.setStatus(HttpStatus.CREATED.name());
        responseDTO.setTimestamp(LocalDateTime.now());
        responseDTO.setUri("/api/v1/enrollments/" + enrollmentDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<EnrollmentResponseDTO>> findAll() {
        return ResponseEntity.ok(enrollmentService.findAll());
    }

    @GetMapping("/findAllHeaders")
    public ResponseEntity<List<EnrollmentResponseDTO>> findAllHeaders(@RequestParam(value = "studentCode", required = false) String studentCode) {
        return ResponseEntity.ok(enrollmentService.findAllHeaders(studentCode));
    }

    @GetMapping("/findBy")
    public ResponseEntity<EnrollmentResponseDTO> findBy(@RequestParam("studentCode") String studentCode, @RequestParam("academicPeriod") String academicPeriod) {
        return ResponseEntity.ok(enrollmentService.findBy(studentCode, academicPeriod));
    }

    @GetMapping("/searchBy")
    public ResponseEntity<EnrollmentDTO> searchBy(@RequestParam("studentCode") String studentCode, @RequestParam("academicPeriod") String academicPeriod) {
        return ResponseEntity.ok(enrollmentService.searchBy(studentCode, academicPeriod));
    }

    @GetMapping("/searchAllHeaders")
    public ResponseEntity<List<EnrollmentDTO>> searchAllHeaders(@RequestParam(value = "studentCode", required = false) String studentCode) {
        return ResponseEntity.ok(enrollmentService.searchAllHeaders(studentCode));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EnrollmentInfoDTO>> getAll(@RequestParam("studentCode") String studentCode, @RequestParam("academicPeriod") String academicPeriod) {
        List<EnrollmentInfoDTO> enrollments = enrollmentService.getAll(studentCode, academicPeriod);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/getBy")
    public ResponseEntity<EnrollmentResponseDTO> getBy(@RequestParam("studentCode") String studentCode, @RequestParam("academicPeriod") String academicPeriod) {
        return ResponseEntity.ok(enrollmentService.getBy(studentCode, academicPeriod));
    }

    @GetMapping("/getAllHeaders")
    public ResponseEntity<List<EnrollmentResponseDTO>> getAllHeaders(@RequestParam(value = "studentCode", required = false) String studentCode) {
        return ResponseEntity.ok(enrollmentService.getAllHeaders(studentCode));
    }

    // comentarios de rapidez:
    // 1) los metodos find (JPA Entities con Lazy loading) es mas rapido que los metodos get (que usan Projections), a pesar que con Projections el # de consultas es menor.
    // Lo unico es que con JPA Entities no se puede hacer uso de columnas calculas, ni seleccionar determinados campos a ser devueltos por el SELECT
    // 2) findAll, consultando solo la data de los headers (no se activa Lazy Loading para consultar el detalle de cada uno), es casi igual en rapidez que el Query Method (con native query = true) de la misma data de la entidad header

}
