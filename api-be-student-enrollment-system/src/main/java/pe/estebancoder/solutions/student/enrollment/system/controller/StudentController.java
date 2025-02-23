package pe.estebancoder.solutions.student.enrollment.system.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.StudentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.StudentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDto) {
        return ResponseEntity.ok(studentService.createStudent(studentRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequestDTO studentRequestDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping(path = "/search-like-name")
    public ResponseEntity<List<StudentResponseDTO>> findStudentsLikeName(@RequestParam String name) {
        return ResponseEntity.ok(studentService.findStudentsLikeName(name));
    }

    @GetMapping(path = "/search-by-dni")
    public ResponseEntity<StudentResponseDTO> findStudentByDNI(@RequestParam String dni) {
        StudentResponseDTO studentResponseDTO = studentService.findStudentByDNI(dni);
        return ResponseEntity.ok(studentResponseDTO);
    }

    @GetMapping(path = "/search-by-studentcode")
    public ResponseEntity<StudentResponseDTO> findStudentByStudentCode(@RequestParam String studentCode) {
        StudentResponseDTO studentResponseDTO = studentService.findStudentByStudentCode(studentCode);
        return ResponseEntity.ok(studentResponseDTO);
    }
}

