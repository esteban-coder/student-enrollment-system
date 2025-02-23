package pe.estebancoder.solutions.student.enrollment.system.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.StudentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.StudentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentEntity;
import pe.estebancoder.solutions.student.enrollment.system.enums.StudentStatus;
import pe.estebancoder.solutions.student.enrollment.system.mapper.StudentMapper;
import pe.estebancoder.solutions.student.enrollment.system.repository.StudentRepository;
import pe.estebancoder.solutions.student.enrollment.system.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final StudentCodeGeneratorService studentCodeGeneratorService;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, StudentCodeGeneratorService studentCodeGeneratorService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.studentCodeGeneratorService = studentCodeGeneratorService;
    }

    @Transactional
    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        //if(studentRepository.countByDni(createStudentRequestDTO.getDni())>0){
        if(studentRepository.countByEmailOrDniAndStatus(studentRequestDTO.getEmail(), studentRequestDTO.getDni(), StudentStatus.ACTIVE.getCode()) > 0){
            throw new RuntimeException("Student already registered with either DNI or Email");
        }

        StudentEntity studentEntity = studentMapper.toEntity(studentRequestDTO);

        String studentCode = studentCodeGeneratorService.generateStudentCode(2024);
        studentEntity.setStudentCode(studentCode);

        studentEntity = studentRepository.save(studentEntity);
        return studentMapper.toDTO(studentEntity);
    }

    @Override
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        StudentEntity existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if(!existingStudent.getDni().equals(studentRequestDTO.getDni()) &&
                studentRepository.countByDniAndStatus(studentRequestDTO.getDni(), StudentStatus.ACTIVE.getCode()) > 0){
            throw new RuntimeException("Student already registered with DNI");
        }
        if(!existingStudent.getEmail().equals(studentRequestDTO.getEmail()) &&
                studentRepository.countByEmailAndStatus(studentRequestDTO.getEmail(), StudentStatus.ACTIVE.getCode()) > 0){
            throw new RuntimeException("Student already registered with Email");
        }

        studentMapper.updateEntityFromDto(studentRequestDTO, existingStudent); // Actualiza la entidad con los valores del DTO
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

    @Override
    public List<StudentResponseDTO> findStudentsLikeName(String name) {
        List<StudentEntity> students = studentRepository.findLikeName("%" + name + "%");
        return studentMapper.toListDTO(students);
    }

    @Override
    public StudentResponseDTO findStudentByDNI(String dni) {
        StudentEntity studentEntity = studentRepository.findByDNI(dni)
                .orElseThrow(() -> new RuntimeException("Student not found by dni"));
        return studentMapper.toDTO(studentEntity);
    }

    @Override
    public StudentResponseDTO findStudentByStudentCode(String studentCode) {
        StudentEntity studentEntity = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Student not found by StudentCode"));
        return studentMapper.toDTO(studentEntity);
    }
}
