package com.niveka.repository.search;

import com.niveka.domain.Fichier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Fichier entity.
 */
public interface FichierSearchRepository extends ElasticsearchRepository<Fichier, String> {
}
