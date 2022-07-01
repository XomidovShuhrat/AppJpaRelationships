package uz.pdp.appjparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appjparelationships.entity.Univercity;

@Repository
public interface UnivercityRepository extends JpaRepository<Univercity,Integer> {
}
