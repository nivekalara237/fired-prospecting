package com.niveka.repository.search;

import com.niveka.domain.Objet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Objet entity.
 */
public interface ObjetSearchRepository extends ElasticsearchRepository<Objet, String> {
}
