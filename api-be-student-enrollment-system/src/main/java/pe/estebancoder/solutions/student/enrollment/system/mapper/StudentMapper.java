package pe.estebancoder.solutions.student.enrollment.system.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pe.estebancoder.solutions.student.enrollment.system.dto.StudentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.StudentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentEntity;

import java.util.List;

@Component
public class StudentMapper {
    private final ModelMapper modelMapper;

    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StudentResponseDTO toDTO(StudentEntity e) {
        return modelMapper.map(e, StudentResponseDTO.class);
    }

    public List<StudentResponseDTO> toListDTO(List<StudentEntity> lstE) {
        return lstE.stream().map(e-> toDTO(e)).toList();
    }

    public StudentEntity toEntity(StudentRequestDTO d) {
        return modelMapper.map(d,StudentEntity.class);
    }

    public List<StudentEntity> toListEntity(List<StudentRequestDTO> lstD) {
        return lstD.stream().map(e-> toEntity(e)).toList();
    }

    // Metodo para actualizar una entidad existente con los valores del DTO
    public void updateEntityFromDto(StudentRequestDTO dto, StudentEntity existingEntity) {
        modelMapper.map(dto, existingEntity); // Este metodo actualiza los campos del DTO en la entidad existente
    }
}
