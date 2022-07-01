package uz.pdp.appjparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appjparelationships.entity.Group;

import java.util.List;

@Repository
public interface RepositoryGroup extends JpaRepository<Group,Integer> {

    List<Group> findAllByFaculty_UnivercityId(Integer faculty_univercity_id);

    @Query("SELECT g FROM groups g WHERE g.faculty.univercity.id=:univercityId")
    List<Group> getGroupsByUnivercityId(Integer univercityId);

    @Query(value = "SELECT * FROM groups gr join faculty f on f.id=gr.faculty_id join univercity univ on univ.id=f.univercity_id where univ.id=:univercityId", nativeQuery = true)
    List<Group> getGroupsByUnivercityIdNative(Integer univercityId);

    boolean existsByNameAndFaculty_Id(String name, Integer faculty_id);
}
