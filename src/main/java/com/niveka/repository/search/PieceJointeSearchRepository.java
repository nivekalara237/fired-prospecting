package com.niveka.repository.search;

import com.niveka.domain.PieceJointe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PieceJointe entity.
 */
public interface PieceJointeSearchRepository extends ElasticsearchRepository<PieceJointe, String> {
}
