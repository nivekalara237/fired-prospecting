package com.niveka.repository.search;

import com.niveka.domain.Prospect;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Prospect entity.
 */
public interface ProspectSearchRepository extends ElasticsearchRepository<Prospect, String> {
}
