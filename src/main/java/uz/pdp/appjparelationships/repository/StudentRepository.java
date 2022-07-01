package uz.pdp.appjparelationships.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appjparelationships.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query(value = "SELECT * FROM student join groups on student.group_id=groups.id join faculty f on groups.faculty_id = f.id join univercity u on f.univercity_id = u.id\n" +
            "WHERE univercity_id=:univercityId", nativeQuery = true)
    List<Student> getStudentByUnivercityId(Integer univercityId);

    @Query(value = "SELECT * FROM student join groups g on student.group_id = g.id \n" +
            "WHERE g.faculty_id=:facultyId", nativeQuery = true)
    List<Student> getStudentsByFacultyId(Integer facultyId);

    @Query(value = "SELECT * FROM student WHERE group_id=:groupId",nativeQuery = true)
    List<Student> getStudentsByGroupId(Integer groupId);

    Page<Student> findAllByGroup_Faculty_UnivercityId(Integer group_faculty_univercity_id, Pageable pageable);
}
