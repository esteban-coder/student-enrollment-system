package pe.estebancoder.solutions.student.enrollment.system.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.StudentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.StudentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentEntity;
import pe.estebancoder.solutions.student.enrollment.system.enums.Gender;
import pe.estebancoder.solutions.student.enrollment.system.enums.StudentStatus;

import java.util.List;

@Component
public class StudentMapper {
    private final ModelMapper modelMapper;

    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Mapeo desde la entidad hacia el DTO (de StudentStatus a su código)
        modelMapper.typeMap(StudentEntity.class, StudentResponseDTO.class)
                .addMappings(mapper -> mapper.using(context -> {
                    StudentStatus status = StudentStatus.fromCode((String) context.getSource());
                    return status != null ? status.name() : null;
                }).map(StudentEntity::getStatus, StudentResponseDTO::setStatusText));

        // Voy a necesitar el codigo tambien, pues al querer actualizar un Student en el FrontEnd en base al codigo voy a selecionar de un combobox, es distinto con el status, pues no es seleccionable es solo lectura en el FrontEnd
        // Mapeo desde la entidad hacia el DTO (de Gender a su nombre enum)
        modelMapper.typeMap(StudentEntity.class, StudentResponseDTO.class)
                .addMappings(mapper -> mapper.using(context -> {
                    Gender gender = Gender.fromCode((String) context.getSource());
                    return gender != null ? gender.name() : null;
                }).map(StudentEntity::getGender, StudentResponseDTO::setGenderText));

        // de DTO a Entity no es requerido pues Entity tambien recibe como codigo
    }

    public StudentResponseDTO toDTO(StudentEntity e) {
        return modelMapper.map(e, StudentResponseDTO.class);
    }

    public List<StudentResponseDTO> toListDTO(List<StudentEntity> lstE) {
        return lstE.stream().map(e -> toDTO(e)).toList();
    }

    public StudentEntity toEntity(StudentRequestDTO d) {
        return modelMapper.map(d, StudentEntity.class);
    }

    public List<StudentEntity> toListEntity(List<StudentRequestDTO> lstD) {
        return lstD.stream().map(e -> toEntity(e)).toList();
    }

    // Metodo para actualizar una entidad existente con los valores del DTO
    public void updateEntityFromDto(StudentRequestDTO dto, StudentEntity existingEntity) {
        modelMapper.map(dto, existingEntity); // Este metodo actualiza los campos del DTO en la entidad existente
    }
}
