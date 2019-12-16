package ch.heigvd.amt.projectTwo.repositories;

import ch.heigvd.amt.projectTwo.entities.MatchEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchesRepository extends CrudRepository<MatchEntity, Integer>{
    List<MatchEntity> findAllByUserId(int userId);
}