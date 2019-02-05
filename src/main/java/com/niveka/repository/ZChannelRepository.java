package com.niveka.repository;

import com.niveka.domain.ZChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the ZChannel entity.
 */
@SuppressWarnings("unused")
public interface ZChannelRepository extends MongoRepository<ZChannel, String> {
    @Query("{}")
    Page<ZChannel> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<ZChannel> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<ZChannel> findOneWithEagerRelationships(String id);

}
