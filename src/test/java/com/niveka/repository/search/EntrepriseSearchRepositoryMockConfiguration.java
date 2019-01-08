package com.niveka.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of EntrepriseSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EntrepriseSearchRepositoryMockConfiguration {

    @MockBean
    private EntrepriseSearchRepository mockEntrepriseSearchRepository;

}
