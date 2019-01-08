package com.niveka.repository.search;

import com.niveka.domain.Channel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Channel entity.
 */
public interface ChannelSearchRepository extends ElasticsearchRepository<Channel, String> {
}
