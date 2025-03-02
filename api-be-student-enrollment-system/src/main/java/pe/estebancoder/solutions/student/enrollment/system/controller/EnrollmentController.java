package pe.estebancoder.solutions.student.enrollment.system.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    public ResponseEntity<List<EnrollmentInfoDTO>> getAll(@RequestParam(value = "studentCode", required = false) String studentCode, @RequestParam(value = "academicPeriod", required = false) String academicPeriod) {
        List<EnrollmentInfoDTO> enrollments = enrollmentService.getAll(studentCode, academicPeriod);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/searchAll")
    public ResponseEntity<List<EnrollmentDTO>> searchAll(@RequestParam(value = "studentCode", required = false) String studentCode, @RequestParam(value = "academicPeriod", required = false) String academicPeriod) {
        List<EnrollmentDTO> enrollments = enrollmentService.searchAll(studentCode, academicPeriod);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/getBy")
    public ResponseEntity<EnrollmentResponseDTO> getBy(@RequestParam("studentCode") String studentCode, @RequestParam("academicPeriod") String academicPeriod) {
        EnrollmentResponseDTO enrollmentResponseDTO = enrollmentService.getBy(studentCode, academicPeriod);
        log.info("Fin");
        return ResponseEntity.ok(enrollmentResponseDTO);
    }

    @GetMapping("/getAllHeaders")
    public ResponseEntity<List<EnrollmentResponseDTO>> getAllHeaders(@RequestParam(value = "studentCode", required = false) String studentCode) {
        return ResponseEntity.ok(enrollmentService.getAllHeaders(studentCode));
    }

    // comentarios de rapidez:
    // 1) los metodos "findBy" o "searchBy" (Query Methods) son iguales solo usan distintos mapeos, y son mas rapido que el metodo "getBy" (que usa Projections nativeQuery), a pesar que con Projections el # de consultas es menor.
    // Esto se debe a que en las consultas de los projections hay muchos joins? en los metodos find son varias querys pero de sus PK.
    // Nota: Con JPA Entities no se puede hacer uso de columnas calculas, ni seleccionar determinados campos a ser devueltos por el SELECT (esto ultimo esta en duda)
    // 2) "findAll" (inherited) es rapido, "getAll" (usa Projection nativeQuery) sin o con parametros, es menos rapido, se deberá a la complejidad del query con varios joins?
    // con findAll son varios querys (N+1) pero se ejecutan rapido, quizas por el where sobre PKs.
    // se probó "searchAll" que es JPQL (activando el fetch para que sea solo una query) sin o con parametros, demora igual que "getAll". Projections no es el problema.
    // 3) "getAllHeaders" (consulta personalidaza con native query) es rapido. "searchAllHeaders" y "findAllHeaders" son iguales solo usan distintos mapeos, si viene parametro (Query Method) tambien es rapido.
    // Conclusion: es un tema de los joins, si son joins de 6 tablas sera muy costoso la consulta
}
