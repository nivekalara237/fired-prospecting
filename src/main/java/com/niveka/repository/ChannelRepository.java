package com.niveka.repository;

import com.niveka.domain.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Channel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelRepository extends MongoRepository<Channel, String> {
    @Query("{}")
    Page<Channel> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Channel> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Channel> findOneWithEagerRelationships(String id);

}
