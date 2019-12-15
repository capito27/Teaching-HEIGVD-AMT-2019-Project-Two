package ch.heigvd.amt.projectTwo.repositories;
import ch.heigvd.amt.projectTwo.entities.TeamEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface TeamsRepository extends CrudRepository<TeamEntity, Integer>{

}