package pe.estebancoder.solutions.student.enrollment.system.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.CreateStudentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.StudentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentEntity;
import pe.estebancoder.solutions.student.enrollment.system.mapper.StudentMapper;
import pe.estebancoder.solutions.student.enrollment.system.repository.StudentRepository;
import pe.estebancoder.solutions.student.enrollment.system.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentResponseDTO createStudent(CreateStudentRequestDTO createStudentRequestDTO) {
        StudentEntity studentEntity = studentMapper.toEntity(createStudentRequestDTO);
        studentEntity = studentRepository.save(studentEntity);
        return studentMapper.toDTO(studentEntity);
    }

    @Override
    public StudentResponseDTO updateStudent(Long id, CreateStudentRequestDTO createStudentRequestDTO) {
        StudentEntity existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentMapper.updateEntityFromDto(createStudentRequestDTO, existingStudent); // Actualiza la entidad con los valores del DTO
        existingStudent = studentRepository.save(existingStudent);

        return studentMapper.toDTO(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public StudentResponseDTO getStudentById(Long id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return studentMapper.toDTO(studentEntity);
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        List<StudentEntity> students = studentRepository.findAll();
        return studentMapper.toListDTO(students);
    }
}
