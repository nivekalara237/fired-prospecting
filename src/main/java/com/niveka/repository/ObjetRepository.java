package com.niveka.repository;

import com.niveka.domain.Objet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data MongoDB repository for the Objet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObjetRepository extends MongoRepository<Objet, String> {
    public List<Objet> findAllByRapportId(String rapportId);
}
