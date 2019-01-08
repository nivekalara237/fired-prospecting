package com.niveka.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of SuiviSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SuiviSearchRepositoryMockConfiguration {

    @MockBean
    private SuiviSearchRepository mockSuiviSearchRepository;

}
