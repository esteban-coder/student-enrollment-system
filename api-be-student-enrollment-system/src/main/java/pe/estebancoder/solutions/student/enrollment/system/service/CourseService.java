package pe.estebancoder.solutions.student.enrollment.system.service;

import pe.estebancoder.solutions.student.enrollment.system.repository.projection.CourseInfo;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.CourseInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.CourseWithSectionsDTO;

import java.util.List;

public interface CourseService {

    List<CourseWithSectionsDTO> findAllByAcademicPeriod(String academicPeriod);
    List<CourseInfoDTO> getAllByAcademicPeriod(String academicPeriod);
    List<CourseInfo> getAllByAcademicPeriod_2(String academicPeriod);
    List<CourseInfo> getAllByAcademicPeriod_3(String academicPeriod);
}
