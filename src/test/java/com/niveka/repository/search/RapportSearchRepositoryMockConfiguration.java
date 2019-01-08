package com.niveka.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of RapportSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RapportSearchRepositoryMockConfiguration {

    @MockBean
    private RapportSearchRepository mockRapportSearchRepository;

}
