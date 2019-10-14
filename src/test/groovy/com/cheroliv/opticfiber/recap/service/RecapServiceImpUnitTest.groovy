package com.cheroliv.opticfiber.recap.service

import com.cheroliv.opticfiber.TestData
import com.cheroliv.opticfiber.inter.repository.InterRepository
import com.cheroliv.opticfiber.inter.service.InterDataService
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

import static org.mockito.BDDMockito.given
import static org.mockito.MockitoAnnotations.initMocks

@Slf4j
@TypeChecked
@ExtendWith(MockitoExtension)
@TestMethodOrder(OrderAnnotation)
@DisplayName('RecapServiceImpUnitTest')
class RecapServiceImpUnitTest {
    @BeforeAll
    static void init() { initMocks(this) }
    TestData data = TestData.instance

    @InjectMocks
    RecapServiceImp recapService
    @Mock
    InterDataService dataService
    @Mock
    InterRepository repo


    @Test
    @Order(1)
    @DisplayName('testNomFeuilles_database_is_empty')
    void testNomFeuilles_database_is_empty() {
        given(dataService.findAllMoisFormatFrParAnnee())
                .willReturn([])
        assert [] == recapService.nomFeuilles()
    }

    @Test
    @Order(2)
    @DisplayName('testNomFeuilles')
    void testNomFeuilles() {
        given(dataService.findAllMoisFormatFrParAnnee())
                .willReturn([['octobre': 2018],
                             ['décembre': 2018],
                             ['janvier': 2019]] as List<Map>)
        assert recapService.nomFeuilles()
                .eachWithIndex { String it, int idx ->
                    it == ['octobre 2018',
                           'décembre 2018',
                           'janvier 2019'].get(idx)
                }
    }
}