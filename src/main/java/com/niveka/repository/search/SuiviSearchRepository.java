package com.niveka.repository.search;

import com.niveka.domain.Suivi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Suivi entity.
 */
public interface SuiviSearchRepository extends ElasticsearchRepository<Suivi, String> {
}
