package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.dto.StudentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    StudentResponseDTO createStudent(StudentRequestDTO studentRequestDto);
    StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDto);
    void deleteStudent(Long id);
    StudentResponseDTO getStudentById(Long id);
    List<StudentResponseDTO> getAllStudents();
}
