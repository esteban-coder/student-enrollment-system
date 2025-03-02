package pe.estebancoder.solutions.student.enrollment.system.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pe.estebancoder.solutions.student.enrollment.system.dto.CourseDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.CourseWithSectionsDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.InstructorDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.SectionDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.CourseEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.InstructorEntity;
import pe.estebancoder.solutions.student.enrollment.system.entity.SectionEntity;

import java.util.List;

@Component
public class CourseMapper {
    private final ModelMapper modelMapper;

    public CourseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CourseWithSectionsDTO toDTO (CourseEntity entity){
        return modelMapper.map(entity, CourseWithSectionsDTO.class);
    }

    public List<CourseWithSectionsDTO> toDTOList (List<CourseEntity> entities){
        return entities.stream().map(this::toDTO).toList();
    }

    public List<CourseWithSectionsDTO> toDtoList (List<CourseEntity> entities){
        return entities.stream().map(this::toDto).toList();
    }

    public CourseWithSectionsDTO toDto(CourseEntity entity) {
        CourseWithSectionsDTO dto = new CourseWithSectionsDTO();

        dto.setId(entity.getId());
        dto.setCourseCode(entity.getCourseCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCredits(entity.getCredits());
        dto.setPrerequisites(entity.getPrerequisites());
        dto.setStatus(entity.getStatus());

        // Mapeamos la lista de secciones, asumiendo que tienes un metodo para convertir SectionEntity a SectionDTO
        dto.setSections(entity.getSections().stream().map(this::toSectionDTO).toList());

        return dto;
    }

    private SectionDTO toSectionDTO(SectionEntity sectionEntity) {
        SectionDTO sectionDTO = new SectionDTO();

        sectionDTO.setId(sectionEntity.getId());
        sectionDTO.setSectionCode(sectionEntity.getSectionCode());
        sectionDTO.setAcademicPeriod(sectionEntity.getAcademicPeriod());
        sectionDTO.setStartDate(sectionEntity.getStartDate());
        sectionDTO.setEndDate(sectionEntity.getEndDate());
        sectionDTO.setSchedule(sectionEntity.getSchedule());
        sectionDTO.setRoomNumber(sectionEntity.getRoomNumber());
        sectionDTO.setMaxCapacity(sectionEntity.getMaxCapacity());
        sectionDTO.setEnrolledStudentCount(sectionEntity.getEnrolledStudentCount());
        sectionDTO.setStatus(sectionEntity.getStatus());

        // Mapeo del instructor
        sectionDTO.setInstructor(toInstructorDTO(sectionEntity.getInstructor()));

        return sectionDTO;
    }

    private InstructorDTO toInstructorDTO(InstructorEntity instructorEntity) {
        InstructorDTO instructorDTO = new InstructorDTO();

        instructorDTO.setId(instructorEntity.getId());
        instructorDTO.setDni(instructorEntity.getDni());
        instructorDTO.setFullName(instructorEntity.getFullName());
        instructorDTO.setEmail(instructorEntity.getEmail());
        instructorDTO.setPhoneNumber(instructorEntity.getPhoneNumber());
        instructorDTO.setAcademicDegree(instructorEntity.getAcademicDegree());
        instructorDTO.setHireDate(instructorEntity.getHireDate());
        instructorDTO.setStatus(instructorEntity.getStatus());

        return instructorDTO;
    }


}
