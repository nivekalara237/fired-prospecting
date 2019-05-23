package com.niveka.repository;

import com.niveka.domain.Prospect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Prospect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProspectRepository extends MongoRepository<Prospect, String> {
    Page<Prospect> findAllByUserId(Pageable pageable, String userId);
}
