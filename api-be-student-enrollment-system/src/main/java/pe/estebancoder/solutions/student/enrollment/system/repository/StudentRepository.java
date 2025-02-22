package pe.estebancoder.solutions.student.enrollment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query(value = "SELECT * FROM TBL_STUDENT WHERE NAME LIKE :name AND STATUS = '1'", nativeQuery = true)
    List<StudentEntity> findLikeName(@Param("name") String name);

    @Query(value = "SELECT * FROM TBL_STUDENT WHERE DNI = :dni AND STATUS = '1'", nativeQuery = true)
    Optional<StudentEntity> findByDNI(@Param("dni") String dni);

    @Modifying
    @Query(value = "update TBL_STUDENT set STATUS='0' where STUDENT_ID = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM TBL_STUDENT WHERE DNI = :dni", nativeQuery = true)
    Integer countByDni(@Param("dni") String dni);
}
