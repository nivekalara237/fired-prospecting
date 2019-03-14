package com.niveka.repository;

import com.niveka.domain.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
    public List<Authority> findByEntrepriseId(String entrepriseId);
}
