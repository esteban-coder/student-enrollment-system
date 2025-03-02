package pe.estebancoder.solutions.student.enrollment.system.service.impl;

import org.springframework.stereotype.Service;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.CourseInfo;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.CourseInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.CourseWithSectionsDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.CourseEntity;
import pe.estebancoder.solutions.student.enrollment.system.mapper.CourseMapper;
import pe.estebancoder.solutions.student.enrollment.system.repository.CourseRepository;
import pe.estebancoder.solutions.student.enrollment.system.service.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<CourseWithSectionsDTO> findAllByAcademicPeriod(String academicPeriod) {
        List<CourseEntity> courses = courseRepository.findAllByAcademicPeriod(academicPeriod);
        // return courseMapper.toDtoList(courses); // mapeo manual
        return courseMapper.toDTOList(courses); // con ModelMapper
    }

    public List<CourseInfoDTO> getAllByAcademicPeriod(String academicPeriod) {
        List<CourseInfoDTO> courses = courseRepository.getAllByAcademicPeriod(academicPeriod);
        return courses;
    }

    public List<CourseInfo> getAllByAcademicPeriod_2(String academicPeriod) {
        List<CourseInfo> courses = courseRepository.getAllByAcademicPeriod_2(academicPeriod);
        return courses;
    }

    public List<CourseInfo> getAllByAcademicPeriod_3(String academicPeriod) {
        List<CourseInfo> courses = courseRepository.getAllByAcademicPeriod_3(academicPeriod);
        return courses;
    }
}
