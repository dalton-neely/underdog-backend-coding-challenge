package dalton.neely.playerbackend;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
  @Query(value = "SELECT count(*) > 0 FROM players WHERE id=:id", nativeQuery = true)
  boolean existsByIdentification(String id);
  
  @Query(value = "SELECT * FROM players WHERE id=:id", nativeQuery = true)
  Player findByIdentification(String id);
  
  @Query(value = "SELECT * FROM players WHERE sport=:sport", nativeQuery = true)
  List<Player> findAllBySport(String sport);
  
  List<Player> findAllByLastNameLike(String lastInitial);
  
  List<Player> findAllByAge(int age);
  
  List<Player> findAllByAgeGreaterThanEqual(int age);
  
  List<Player> findAllByAgeLessThanEqual(int age);
  
  List<Player> findAllByPosition(String position);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE players SET name_brief=:nameBrief, first_name=:firstName, last_name=:lastName, position=:position, age=:age, average_position_age_diff=:averagePositionAgeDiff WHERE id=:id", nativeQuery = true)
  int updateByIdentification(String id, String nameBrief, String firstName, String lastName, String position, int age, double averagePositionAgeDiff);
}
