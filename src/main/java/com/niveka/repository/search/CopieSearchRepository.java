package com.niveka.repository.search;

import com.niveka.domain.Copie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Copie entity.
 */
public interface CopieSearchRepository extends ElasticsearchRepository<Copie, String> {
}
