package com.niveka.repository.search;

import com.niveka.domain.CompteRenduSuivi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Elasticsearch repository for the CompteRenduSuivi entity.
 */
@Repository
public interface CompteRenduSuiviSearchRepository extends ElasticsearchRepository<CompteRenduSuivi, String> {
}
