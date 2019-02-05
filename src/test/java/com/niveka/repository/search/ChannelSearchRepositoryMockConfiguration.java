package com.niveka.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ZChannelSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ChannelSearchRepositoryMockConfiguration {

    @MockBean
    private ZChannelSearchRepository mockZChannelSearchRepository;

}
