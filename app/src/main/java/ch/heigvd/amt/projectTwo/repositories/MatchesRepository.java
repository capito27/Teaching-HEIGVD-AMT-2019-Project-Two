package ch.heigvd.amt.projectTwo.repositories;

import ch.heigvd.amt.projectTwo.entities.MatchEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MatchesRepository extends CrudRepository<MatchEntity, Integer> {
    List<MatchEntity> findAllByUserId(int userId);

    // used to get data in a pageable fashion
    @Cacheable("matchListPaged")
    List<MatchEntity> findByUserId(int userId, Pageable pageable);

    @Cacheable("matchCount")
    long countByUserId(int userId);
}