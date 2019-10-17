package com.cheroliv.opticfiber.recap.service


import com.cheroliv.opticfiber.TestData
import com.cheroliv.opticfiber.inter.repository.InterRepository
import com.cheroliv.opticfiber.inter.service.InterDataService
import com.cheroliv.opticfiber.recap.service.exceptions.RecapNoInterInRepositoryException
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
import static org.apache.commons.io.FilenameUtils.getExtension
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
    @DisplayName('testIsInterRepositoryConsistent_dates_is_null')
    void testIsInterRepositoryConsistent_dates_is_null()
            throws RecapNoInterInRepositoryException {
        given(repo.findAllDates())
                .willReturn(null)
        Assertions.assertThrows(
                RecapNoInterInRepositoryException as Class<Throwable>,
                { ->
                    recapServiceImp.checkInterRepositoryConsistent()
                })
    }

    @Test
    @Order(4)
    @DisplayName('testIsInterRepositoryConsistent_dates_is_empty')
    void testIsInterRepositoryConsistent_dates_is_empty()
            throws RecapNoInterInRepositoryException {
        given(repo.findAllDates())
                .willReturn([])
        Assertions.assertThrows(
                RecapNoInterInRepositoryException as Class<Throwable>,
                { ->
                    recapServiceImp.checkInterRepositoryConsistent()
                })
    }

    @Test
    @Order(5)
    @DisplayName('testIsInterRepositoryConsistent_dates_is_not_empty')
    void testIsInterRepositoryConsistent_dates_is_not_empty() {
        given(repo.findAllDates())
                .willReturn(data.dates)
        recapServiceImp.checkInterRepositoryConsistent()
    }

    @Test
    @Order(6)
    @DisplayName('testGenerateRecapFileName_inter_repository_is_null')
    void testGenerateRecapFileName_inter_repository_is_not_consistent()
            throws RecapNoInterInRepositoryException {
        given(repo.findAllDates())
                .willReturn(null)
        Assertions.assertThrows(
                RecapNoInterInRepositoryException as Class<Throwable>,
                { ->
                    recapServiceImp.generateRecapFileName(
                            data.dates.first(),
                            data.dates.last())
                })

    }

    @Test
    @Order(7)
    @DisplayName('testGenerateRecapFileName_inter_repository_is_empty')
    void testGenerateRecapFileName_inter_repository_is_empty()
            throws RecapNoInterInRepositoryException {
        given(repo.findAllDates())
                .willReturn([])
        Assertions.assertThrows(
                RecapNoInterInRepositoryException as Class<Throwable>,
                { ->
                    recapServiceImp.generateRecapFileName(
                            data.dates.first(),
                            data.dates.last())
                })
    }


    @Test
    @Order(6)
    @DisplayName('testGenerateRecapFileName_startDate_is_null_and_endDate_\u2208_dates')
    void testGenerateRecapFileName_startDate_is_null_and_endDate_element_of_dates() {
        given(repo.findAllDates())
                .willReturn(data.dates)
        LocalDateTime startDate = null
        LocalDateTime endDate = data.nextInterDto.dateTime
        LocalDateTime oldestDate = data.dates.first()

        assert 'recap_' +
                dateTimeFormattedForFileName(oldestDate) +
                '_-_' +
                dateTimeFormattedForFileName(endDate) +
                '.' +
                getExtension(recapSpreadsheetFileName) ==
                this.recapServiceImp.generateRecapFileName(
                        startDate, endDate)
    }

    @Test
    @Order(7)
    @DisplayName('testGenerateRecapFileName_endDate_is_null_and_startDate_element_of_dates')
    void testGenerateRecapFileName_endDate_is_null_and_startDate_element_of_dates() {
        given(repo.findAllDates())
                .willReturn(data.dates)
        LocalDateTime startDate = data.prevInterDto.dateTime
        LocalDateTime endDate = null
        LocalDateTime latestDate = data.dates.last()
        assert 'recap_' +
                dateTimeFormattedForFileName(startDate) +
                '_-_' +
                dateTimeFormattedForFileName(latestDate) +
                '.' +
                getExtension(recapSpreadsheetFileName) ==
                this.recapServiceImp.generateRecapFileName(
                        startDate, endDate)
    }

    @Test
    @Order(8)
    @DisplayName('testGenerateRecapFileName_endDate_or_startDate_is_not_element_of_dates')
    void testGenerateRecapFileName_endDate_or_startDate_is_not_element_of_dates() {
        given(repo.findAllDates())
                .willReturn(data.dates)
        LocalDateTime startDate = data.dates.first().minusYears(100)
        LocalDateTime endDate = data.dates.last().plusYears(100)
        LocalDateTime oldestDate = data.dates.first()
        LocalDateTime latestDate = data.dates.last()
        assert startDate.isBefore(data.dates.min())
        assert endDate.isAfter(data.dates.max())

        String expectedResult = 'recap_' +
                dateTimeFormattedForFileName(oldestDate) +
                '_-_' +
                dateTimeFormattedForFileName(latestDate) +
                '.' +
                getExtension(recapSpreadsheetFileName)

        assert this.recapServiceImp.generateRecapFileName(
                startDate, endDate) == expectedResult

        assert this.recapServiceImp.generateRecapFileName(
                data.prevInterDto.dateTime, endDate) == expectedResult

        assert this.recapServiceImp.generateRecapFileName(
                startDate, data.nextInterDto.dateTime) == expectedResult


    }


    @Test
    @Order(9)
    @DisplayName('testGenerateRecapFileName_endDate_and_startDate_is_element_of_dates')
    void testGenerateRecapFileName_endDate_and_startDate_is_element_of_dates() {
        given(repo.findAllDates())
                .willReturn(data.dates)
        LocalDateTime startDate = data.prevInterDto.dateTime
        LocalDateTime endDate = data.nextInterDto.dateTime
        assert 'recap_' +
                dateTimeFormattedForFileName(startDate) +
                '_-_' +
                dateTimeFormattedForFileName(endDate) +
                '.' +
                getExtension(recapSpreadsheetFileName) ==
                this.recapServiceImp.generateRecapFileName(
                        startDate, endDate)
        //even if I invert date order
        assert 'recap_' +
                dateTimeFormattedForFileName(startDate) +
                '_-_' +
                dateTimeFormattedForFileName(endDate) +
                '.' +
                getExtension(recapSpreadsheetFileName) ==
                this.recapServiceImp.generateRecapFileName(
                        endDate, startDate)
    }



//    String generateRecapFileName(
//            LocalDateTime startDate
//            LocalDateTime endDate

//    @Test
//    @Order(3)
//    @DisplayName("testGenerateRecapFileName")
//    void testGenerateRecapFileName() {
//        LocalDateTime startDate
//        LocalDateTime endDate
//    }
}