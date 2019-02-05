package com.niveka.repository;

import com.niveka.domain.Entreprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Entreprise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntrepriseRepository extends MongoRepository<Entreprise, String> {
    @Query("{}")
    Page<Entreprise> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Entreprise> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Entreprise> findOneWithEagerRelationships(String id);

}
