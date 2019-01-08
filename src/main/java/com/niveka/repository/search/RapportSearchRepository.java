package com.niveka.repository.search;

import com.niveka.domain.Rapport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Rapport entity.
 */
public interface RapportSearchRepository extends ElasticsearchRepository<Rapport, String> {
}
