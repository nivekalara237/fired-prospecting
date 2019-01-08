package com.niveka.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CopieSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CopieSearchRepositoryMockConfiguration {

    @MockBean
    private CopieSearchRepository mockCopieSearchRepository;

}
