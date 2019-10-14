package com.cheroliv.opticfiber.recap.service

import com.cheroliv.opticfiber.ApplicationUtils
import com.cheroliv.opticfiber.TestData
import com.cheroliv.opticfiber.inter.repository.InterRepository
import com.cheroliv.opticfiber.inter.service.InterDataService
import com.cheroliv.opticfiber.recap.service.exceptions.RecapDatesCouldNotBeNull
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

import java.time.LocalDateTime

import static com.cheroliv.opticfiber.ApplicationUtils.dateTimeFormattedForFileName
import static com.cheroliv.opticfiber.TestUtils.applicationProperties
import static org.junit.jupiter.api.Assertions.assertThrows
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
    RecapServiceImp recapServiceImp
    @Mock
    InterDataService dataService
    @Mock
    InterRepository repo

    String recapSpreadsheetFileNameKey =
            'application.data.recap-spreadsheet-file-name'
    String homeDirectoryNameKey =
            'application.data.home-directory-name'
    String recapSpreadsheetDirectoryNameKey =
            'application.data.recap-spreadsheet-directory-name'
    String homeDirectoryName =
            getApplicationProperties()[homeDirectoryNameKey]
    String recapSpreadsheetDirectoryName =
            getApplicationProperties()[recapSpreadsheetDirectoryNameKey]
    String recapSpreadsheetFileName =
            getApplicationProperties()[recapSpreadsheetFileNameKey]


    @BeforeEach
    void setUp() {
        recapServiceImp = new RecapServiceImp(
                homeDirectoryName,
                recapSpreadsheetDirectoryName,
                recapSpreadsheetFileName,
                dataService,
                repo
        )
    }

    @Test
    @Order(1)
    @DisplayName('testNomFeuilles_database_is_empty')
    void testNomFeuilles_database_is_empty() {
        given(dataService.findAllMoisFormatFrParAnnee())
                .willReturn([])
        assert [] == recapServiceImp.nomFeuilles()
    }

    @Test
    @Order(2)
    @DisplayName('testNomFeuilles')
    void testNomFeuilles() {
        given(dataService.findAllMoisFormatFrParAnnee())
                .willReturn([['octobre': 2018],
                             ['décembre': 2018],
                             ['janvier': 2019]] as List<Map>)
        assert recapServiceImp.nomFeuilles()
                .eachWithIndex { String it, int idx ->
                    it == ['octobre 2018',
                           'décembre 2018',
                           'janvier 2019'].get(idx)
                }
    }


    @Test
    @Order(3)
    @DisplayName('testGenerateRecapFileName_startdate_is_after_endDate')
    void testGenerateRecapFileName_startdate_is_after_endDate() {
        LocalDateTime date2 = LocalDateTime.now().minusHours(1)
        LocalDateTime date1 = LocalDateTime.now()
        assert date1 && date2 && date2.isBefore(date1)
        String result = this.recapServiceImp
                .generateRecapFileName(date1, date2)
        assert result.contains(dateTimeFormattedForFileName(date1))
        assert result.contains(dateTimeFormattedForFileName(date2))
        String strDate1 = dateTimeFormattedForFileName(date1)
        String strDate2 = dateTimeFormattedForFileName(date2)
        int index1 = result.lastIndexOf(strDate1)
        int index2 = result.lastIndexOf(strDate2)
        assert index2 < index1
    }

    @Test
    @Order(4)
    @DisplayName('testGenerateRecapFileName_startdate_or_endDate_is_null')
    void testGenerateRecapFileName_startdate_or_endDate_is_null()
            throws RecapDatesCouldNotBeNull {
        LocalDateTime date1 = null
        LocalDateTime date2 = null
        assertThrows(RecapDatesCouldNotBeNull as Class<Throwable>,
                { ->
                    recapServiceImp
                            .generateRecapFileName(date1, date2)
                })
    }

    @Test
    @Order(5)
    @DisplayName('testGenerateRecapFileName')
    void testGenerateRecapFileName(){
        LocalDateTime date1 = LocalDateTime.now().minusHours(1)
        LocalDateTime date2 = LocalDateTime.now()
        assert date1 && date2 && date2.isAfter(date1)
        String result = this.recapServiceImp
                .generateRecapFileName(date1, date2)
        assert result.contains(dateTimeFormattedForFileName(date1))
        assert result.contains(dateTimeFormattedForFileName(date2))
        String strDate1 = dateTimeFormattedForFileName(date1)
        String strDate2 = dateTimeFormattedForFileName(date2)
        int index1 = result.lastIndexOf(strDate1)
        int index2 = result.lastIndexOf(strDate2)
        assert index1 < index2
    }
}