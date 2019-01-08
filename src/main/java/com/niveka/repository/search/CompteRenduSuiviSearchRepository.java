package com.niveka.repository.search;

import com.niveka.domain.CompteRenduSuivi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CompteRenduSuivi entity.
 */
public interface CompteRenduSuiviSearchRepository extends ElasticsearchRepository<CompteRenduSuivi, String> {
}
