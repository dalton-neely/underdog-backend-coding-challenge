package dalton.neely.playerbackend;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Players, Long> {
  @Query(value = "SELECT count(*) > 0 FROM players WHERE id=:id", nativeQuery = true)
  boolean existsByIdentification(String id);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE players SET name_brief=:nameBrief, first_name=:firstName, last_name=:lastName, position=:position, age=:age, average_position_age_diff=:averagePositionAgeDiff WHERE id=:id", nativeQuery = true)
  int updateByIdentification(String id, String nameBrief, String firstName, String lastName, String position, int age, double averagePositionAgeDiff);
}
