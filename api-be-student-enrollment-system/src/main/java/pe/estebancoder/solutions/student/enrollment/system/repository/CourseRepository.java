package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.CourseInfo;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.CourseInfoDTO;
import pe.estebancoder.solutions.student.enrollment.system.entity.CourseEntity;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    // Entre 300 y 400 ms (solo cursos)
    // Si es con las relacionas se da el problema N+1 (> 1.90 seg):
    // @Query("SELECT DISTINCT c FROM CourseEntity c JOIN c.sections s WHERE s.academicPeriod = :academicPeriod")
    /*
    @Query(value = """
            SELECT tc.*
            FROM TBL_COURSE tc
            INNER JOIN TBL_SECTION ts ON tc.COURSE_ID = ts.COURSE_ID
            WHERE ts.ACADEMIC_PERIOD = :academicPeriod
        """, nativeQuery = true)
    */
    /*
    // no es el query optimo
    @Query(value = """
            SELECT tc.*
            FROM TBL_COURSE tc
            INNER JOIN TBL_SECTION ts ON tc.COURSE_ID = ts.COURSE_ID
            INNER JOIN TBL_INSTRUCTOR ti ON ts.INSTRUCTOR_ID = ti.INSTRUCTOR_ID
            WHERE ts.ACADEMIC_PERIOD = :academicPeriod
        """, nativeQuery = true)
    */
    // Queda pendiente saber en los anteriores queries nativos, porque se ejecuta por cada section, un inner join secction/instructor

    // Entre 150 y 210 ms solo cursos
    // Si es con las relaciones se da el problema N+1 (> 1.70 seg):
    // @Query("SELECT c FROM CourseEntity c WHERE c.id IN (SELECT s.course.id FROM SectionEntity s WHERE s.academicPeriod = :academicPeriod)")
    /*
    @Query(value = """
            SELECT tc.* FROM TBL_COURSE tc 
            WHERE tc.COURSE_ID IN (SELECT ts.COURSE_ID FROM TBL_SECTION ts WHERE ts.ACADEMIC_PERIOD = :academicPeriod)
        """, nativeQuery = true)
    */

    // Entre 350 y 450 ms, solo 1 sola consulta
    /*
    @Query("""
            SELECT DISTINCT c FROM CourseEntity c
            JOIN FETCH c.sections s
            JOIN FETCH s.instructor
            WHERE s.academicPeriod = :academicPeriod
      """)
    */

    // Entre 160 y 250 ms, 1 consulta de cursos y sections, y N consultas para instructor
    /**/
    @Query("""
            SELECT DISTINCT c FROM CourseEntity c
            JOIN FETCH c.sections s
            JOIN s.instructor
            WHERE s.academicPeriod = :academicPeriod
      """)
    // es igual si no estuviera JOIN s.instructor
    /**/

    // Entre 500 y 600 ms, solo 1 sola consulta
    /*
    @EntityGraph(attributePaths = {"sections", "sections.instructor"})
    @Query("SELECT c FROM CourseEntity c WHERE c.id IN (SELECT s.course.id FROM SectionEntity s WHERE s.academicPeriod = :academicPeriod)")
    */

    // Entre 350 y 450 ms (no es el query optimo)
    /*
    @EntityGraph(attributePaths = {"sections"})
    @Query("SELECT c FROM CourseEntity c WHERE c.id IN (SELECT s.course.id FROM SectionEntity s WHERE s.academicPeriod = :academicPeriod)")
    */
    List<CourseEntity> findAllByAcademicPeriod(@Param("academicPeriod") String academicPeriod);

    // NOTA: segun Claude, mas optimo es JPQL para resultados masivos, para consultas simples, es imperceptible la diferencia con native query
    // NOTA: se ha comprobado que mapeo con ModelMapper vs mapeo manual, no representa sobretiempo con ModelMapper


    // Consulta única con proyección a DTOs usando constructor expression
    @Query("""
    SELECT new pe.estebancoder.solutions.student.enrollment.system.repository.projection.CourseInfoDTO(
        c.id, c.courseCode, c.name, c.description, c.credits, c.prerequisites, c.status,
        s.id, s.sectionCode, s.academicPeriod, s.schedule, s.roomNumber, s.maxCapacity, s.enrolledStudentCount, s.status,
        i.id, i.fullName, i.email, i.phoneNumber, i.academicDegree)
    FROM CourseEntity c
    JOIN c.sections s
    JOIN s.instructor i
    WHERE s.academicPeriod = :academicPeriod
    ORDER BY c.id, s.sectionCode
    """)
    List<CourseInfoDTO> getAllByAcademicPeriod(@Param("academicPeriod") String academicPeriod);


    @Query("""
    SELECT 
        c.id AS courseId, 
        c.courseCode AS courseCode, 
        c.name AS courseName, 
        c.description AS courseDescription, 
        c.credits AS courseCredits, 
        c.prerequisites AS coursePrerequisites, 
        c.status AS courseStatus,
        s.id AS sectionId, 
        s.sectionCode AS sectionCode, 
        s.academicPeriod AS academicPeriod, 
        s.schedule AS schedule, 
        s.roomNumber AS roomNumber, 
        s.maxCapacity AS maxCapacity, 
        s.enrolledStudentCount AS enrolledStudentCount, 
        s.status AS sectionStatus,
        i.id AS instructorId, 
        i.fullName AS instructorName, 
        i.email AS instructorEmail,
        i.phoneNumber AS instructorPhoneNumber, 
        i.academicDegree AS academicDegree
    FROM CourseEntity c
    JOIN c.sections s
    JOIN s.instructor i
    WHERE s.academicPeriod = :academicPeriod
    ORDER BY c.id, s.sectionCode
    """)
    List<CourseInfo> getAllByAcademicPeriod_2(@Param("academicPeriod") String academicPeriod);



    @Query(value = """
        SELECT 
            c.course_id AS courseId, 
            c.course_code AS courseCode, 
            c.name AS courseName, 
            c.description AS courseDescription, 
            c.credits AS courseCredits, 
            c.prerequisites AS coursePrerequisites, 
            c.status AS courseStatus,
            s.section_id AS sectionId, 
            s.section_code AS sectionCode, 
            s.academic_period AS academicPeriod, 
            s.schedule AS schedule, 
            s.room_number AS roomNumber, 
            s.max_capacity AS maxCapacity, 
            s.enrolled_student_count AS enrolledStudentCount, 
            s.status AS sectionStatus,
            i.instructor_id AS instructorId, 
            i.fullname AS instructorName, 
            i.email AS instructorEmail,
            i.phone_number AS instructorPhoneNumber, 
            i.academic_degree AS academicDegree
        FROM tbl_course c
        JOIN tbl_section s ON c.course_id = s.course_id
        JOIN tbl_instructor i ON s.instructor_id = i.instructor_id
        WHERE s.academic_period = :academicPeriod
        ORDER BY c.course_id, s.section_code
        """, nativeQuery = true)
    List<CourseInfo> getAllByAcademicPeriod_3(@Param("academicPeriod") String academicPeriod);

    // comentarios de rapidez:
    // para los 4 metodos probados, el rango de tiempo es entre 300 a 500ms es igual en los 4
}

