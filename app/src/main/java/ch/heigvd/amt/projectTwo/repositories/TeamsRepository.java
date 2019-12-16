package ch.heigvd.amt.projectTwo.repositories;
import ch.heigvd.amt.projectTwo.entities.StadiumEntity;
import ch.heigvd.amt.projectTwo.entities.TeamEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TeamsRepository extends CrudRepository<TeamEntity, Integer>{
    List<TeamEntity> findAllByUserId(int userId);
}