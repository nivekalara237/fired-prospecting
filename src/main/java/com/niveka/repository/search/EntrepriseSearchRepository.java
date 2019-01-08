package com.niveka.repository.search;

import com.niveka.domain.Entreprise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Entreprise entity.
 */
public interface EntrepriseSearchRepository extends ElasticsearchRepository<Entreprise, String> {
}
