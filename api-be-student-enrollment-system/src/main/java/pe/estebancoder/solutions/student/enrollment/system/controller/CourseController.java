package pe.estebancoder.solutions.student.enrollment.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.CourseInfo;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.CourseInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.CourseWithSectionsDTO;
import pe.estebancoder.solutions.student.enrollment.system.service.CourseService;

import java.util.List;

// @Slf4j
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Este es el que se usará para devolver los cursos y las secciones del periodo 2024-1
    @GetMapping("/findByAcademicPeriod")
    public ResponseEntity<List<CourseWithSectionsDTO>> findAllByAcademicPeriod(String academicPeriod) {
        // log.info("Inicio");
        List<CourseWithSectionsDTO> courses = courseService.findAllByAcademicPeriod(academicPeriod);
        // log.info("Fin");
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/getByAcademicPeriod")
    public ResponseEntity<List<CourseInfoDTO>> getAllByAcademicPeriod(String academicPeriod) {
        // log.info("Inicio");
        List<CourseInfoDTO> courses = courseService.getAllByAcademicPeriod(academicPeriod);
        // log.info("Fin");
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/getByAcademicPeriod_2")
    public ResponseEntity<List<CourseInfo>> getAllByAcademicPeriod_2(String academicPeriod) {
        List<CourseInfo> courses = courseService.getAllByAcademicPeriod_2(academicPeriod);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/getByAcademicPeriod_3")
    public ResponseEntity<List<CourseInfo>> getAllByAcademicPeriod_3(String academicPeriod) {
        List<CourseInfo> courses = courseService.getAllByAcademicPeriod_3(academicPeriod);
        return ResponseEntity.ok(courses);
    }
}
