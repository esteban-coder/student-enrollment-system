package pe.estebancoder.solutions.student.enrollment.system.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pe.estebancoder.solutions.student.enrollment.system.dto.request.CreateSectionRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.response.SectionResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.SectionEntity;

import java.util.List;

@Component
public class SectionMapper {
    private final ModelMapper modelMapper;

    public SectionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SectionResponseDTO toDTO(SectionEntity e) {
        return modelMapper.map(e, SectionResponseDTO.class);
    }

    public List<SectionResponseDTO> toListDTO(List<SectionEntity> lstE) {
        return lstE.stream().map(e-> toDTO(e)).toList();
    }

    public SectionEntity toEntity(CreateSectionRequestDTO d) {
        return modelMapper.map(d,SectionEntity.class);
    }

    public List<SectionEntity> toListEntity(List<CreateSectionRequestDTO> lstD) {
        return lstD.stream().map(e-> toEntity(e)).toList();
    }
}
