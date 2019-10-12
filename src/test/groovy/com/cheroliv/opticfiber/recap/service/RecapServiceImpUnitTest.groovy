package com.cheroliv.opticfiber.recap.service


//import groovy.transform.TypeChecked
//import groovy.util.logging.Slf4j
//import org.junit.jupiter.api.*
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.InjectMocks
//import org.mockito.MockitoAnnotations
//import org.mockito.junit.jupiter.MockitoExtension
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.mock.mockito.MockBean

//import static org.mockito.BDDMockito.given

//@Slf4j
//@TypeChecked
//@WebMvcTest(RecapService)
//@ExtendWith(MockitoExtension)
//@TestMethodOrder(OrderAnnotation)
//@DisplayName('RecapServiceImpUnitTest')
class RecapServiceImpUnitTest {
//    @BeforeAll
//    static void init() {
//        MockitoAnnotations.initMocks(this)
//    }
//    TestData data = TestData.instance
//
//    @InjectMocks
//    RecapService recapService
//    @MockBean
//    InterDataService dataService//= Mockito.mock(InterDataService)
//
//
//    @BeforeEach
//    void setUp() {
//        recapService = new RecapServiceImp(dataService)
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName('testNomFeuilles_database_is_empty')
//    void testNomFeuilles_database_is_empty() {
//        given(dataService.findAllMoisFormatFrParAnnee())
//                .willReturn([])
//        assert [] == recapService.nomFeuilles()
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName('testNomFeuilles')
//    void testNomFeuilles() {
//        given(dataService.findAllMoisFormatFrParAnnee())
//                .willReturn([['octobre': 2018],
//                             ['décembre': 2018],
//                             ['janvier': 2019]] as List<Map>)
//        assert recapService.nomFeuilles()
//                .eachWithIndex { String it, int idx ->
//                    it == ['octobre 2018',
//                           'décembre 2018',
//                           'janvier 2019'].get(idx)
//                }
//    }


}