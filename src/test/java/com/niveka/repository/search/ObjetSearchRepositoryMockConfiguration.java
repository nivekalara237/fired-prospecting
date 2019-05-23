package com.niveka.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PieceJointeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ObjetSearchRepositoryMockConfiguration {

    @MockBean
    private PieceJointeSearchRepository mockPieceJointeSearchRepository;

}
