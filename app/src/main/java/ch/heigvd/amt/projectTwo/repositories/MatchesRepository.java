package ch.heigvd.amt.projectTwo.repositories;

import ch.heigvd.amt.projectTwo.entities.MatchEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MatchesRepository extends CrudRepository<MatchEntity, Integer> {

    // We need to handle cache eviction properly, to prevent incoherent states
    @Override
    @CacheEvict(cacheNames = {"matchListPaged", "matchCount"}, allEntries = true)
    <S extends MatchEntity> S save(S s);

    @Override
    @CacheEvict(cacheNames = {"matchListPaged", "matchCount"}, allEntries = true)
    <S extends MatchEntity> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @CacheEvict(cacheNames = {"matchListPaged", "matchCount"}, allEntries = true)
    void delete(MatchEntity matchEntity);

    @Override
    @CacheEvict(cacheNames = {"matchListPaged", "matchCount"}, allEntries = true)
    void deleteById(Integer integer);

    @Override
    @CacheEvict(cacheNames = {"matchListPaged", "matchCount"}, allEntries = true)
    void deleteAll(Iterable<? extends MatchEntity> iterable);

    @Override
    @CacheEvict(cacheNames = {"matchListPaged", "matchCount"}, allEntries = true)
    void deleteAll();

    List<MatchEntity> findAllByUserId(int userId);

    // used to get data in a pageable fashion
    @Cacheable("matchListPaged")
    List<MatchEntity> findByUserId(int userId, Pageable pageable);

    @Cacheable("matchCount")
    long countByUserId(int userId);
}