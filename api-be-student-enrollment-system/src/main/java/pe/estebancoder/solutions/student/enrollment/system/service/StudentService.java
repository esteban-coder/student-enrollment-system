package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.dto.request.StudentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    StudentResponseDTO createStudent(StudentRequestDTO studentRequestDto);
    StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDto);
    void deleteStudent(Long id);
    StudentResponseDTO getStudentById(Long id);
    List<StudentResponseDTO> getAllStudents();
    List<StudentResponseDTO> findStudentsLikeName(String name);
    StudentResponseDTO findStudentByDNI(String dni);
    StudentResponseDTO findStudentByStudentCode(String studentCode);
}
