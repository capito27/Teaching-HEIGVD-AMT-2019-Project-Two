package ch.heigvd.amt.projectTwo.repositories;

import ch.heigvd.amt.projectTwo.api.model.Stadium;
import ch.heigvd.amt.projectTwo.entities.StadiumEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadiumsRepository extends CrudRepository<StadiumEntity, Integer>{
}
