package com.niveka.repository.search;

import com.niveka.domain.ZChannel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ZChannel entity.
 */
public interface ZChannelSearchRepository extends ElasticsearchRepository<ZChannel, String> {
}
