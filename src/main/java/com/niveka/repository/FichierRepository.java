package com.niveka.repository;

import com.niveka.domain.Fichier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data MongoDB repository for the Fichier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FichierRepository extends MongoRepository<Fichier, String> {
    List<Fichier> findByModelAndModelId(String model,String ModelId);
}
