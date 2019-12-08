package ch.heigvd.amt.projectTwo.repositories;

import ch.heigvd.amt.projectTwo.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface UserRepository extends CrudRepository<UserEntity, String>{

}
