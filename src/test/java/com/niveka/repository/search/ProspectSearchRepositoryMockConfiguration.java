package com.niveka.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ProspectSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProspectSearchRepositoryMockConfiguration {

    @MockBean
    private ProspectSearchRepository mockProspectSearchRepository;

}
