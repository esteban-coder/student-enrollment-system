package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.dto.request.CreateStudentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    StudentResponseDTO createStudent(CreateStudentRequestDTO createStudentRequestDto);
    StudentResponseDTO updateStudent(Long id, CreateStudentRequestDTO createStudentRequestDto);
    void deleteStudent(Long id);
    StudentResponseDTO getStudentById(Long id);
    List<StudentResponseDTO> getAllStudents();
    List<StudentResponseDTO> findStudentsLikeName(String name);
    StudentResponseDTO findStudentByDNI(String dni);
}
