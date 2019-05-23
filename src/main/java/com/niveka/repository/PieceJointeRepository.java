package com.niveka.repository;

import com.niveka.domain.PieceJointe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data MongoDB repository for the PieceJointe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PieceJointeRepository extends MongoRepository<PieceJointe, String> {
    public List<PieceJointe> findAllByRapportId(String rapportId);
}
