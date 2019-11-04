package com.cheroliv.opticfiber.service

import com.cheroliv.opticfiber.util.ApplicationUtils
import com.cheroliv.opticfiber.TestUtils
import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.inter.entity.InterEntity
import com.cheroliv.opticfiber.inter.entity.dao.InterDao
import com.cheroliv.opticfiber.inter.service.InterDataService
import com.cheroliv.opticfiber.inter.service.exception.InterNotFoundException
import com.cheroliv.opticfiber.inter.service.exception.InterTypeEnumException
import com.google.common.collect.Maps
import groovy.json.JsonSlurper
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.data.domain.Sort
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

import javax.validation.Validator
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static com.cheroliv.opticfiber.util.ApplicationUtils.getSeparator
import static com.cheroliv.opticfiber.util.ApplicationUtils.getUserHomePath
import static com.cheroliv.opticfiber.config.ApplicationConstants.DATE_PATTERN_FORMAT
import static com.cheroliv.opticfiber.config.InterConstants.*
import static com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum.CABLE_ROUTING
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@Slf4j
@TypeChecked
@TestMethodOrder(OrderAnnotation)
@SpringBootTest(webEnvironment = NONE)
class InterDataServiceImpIntegrationTest {
    @Autowired
    InterDataService interDataService
    @Value('${application.data.home-directory-name}')
    String homeDirectoryName
    @Value('${application.data.json-backup-file-name}')
    String jsonBackupFileName
    @Autowired
    InterDao interRepository
    @Value('classpath:inters.json')
    Resource jsonFileResource
    @Autowired
    Validator validator

    @BeforeEach
    void setUp() {
        copyJsonFileToHomeDataDirectory()
        populateDB()
    }

    @Transactional
    void truncateDB() {
        interRepository.deleteAll()
    }

    void copyJsonFileToHomeDataDirectory() {
        this.getJsonFile().delete()
        FileUtils.copyFile(jsonFileResource.getFile(),
                this.getJsonFile())
    }

    @Transactional
    void populateDB() {
        if (interRepository.count() == 0)
            jsonData.each {
                InterEntity inter = jsonDataToInter(it)
                if (!validator.validate(inter).empty)
                    validator.validate(inter).each {
                        log.info it.message
                        log.info inter.toString()
                    }
                else interRepository.save inter
            }
    }

    List<Map<String, String>> getJsonData() {
        new JsonSlurper()?.parseText(jsonFileResource
                .file.getText(StandardCharsets.UTF_8.name())
        ) as List<Map<String, String>>
    }

    static InterEntity jsonDataToInter(Map<String, String> strJsonData) {
        LocalDateTime localDateTime = LocalDateTime.of(
                ApplicationUtils.parseStringDateToLocalDate(strJsonData[DATE_INTER_JSON_FIELD_NAME]),
                ApplicationUtils.parseStringHeureToLocalTime(strJsonData[HOUR_INTER_JSON_FIELD_NAME]))

        new InterEntity(
                id: Long.parseLong(strJsonData[ID_INTER_JSON_FIELD_NAME]),
                nd: strJsonData[ND_INTER_JSON_FIELD_NAME],
                lastNameClient: strJsonData[LASTNAME_INTER_JSON_FIELD_NAME],
                firstNameClient: strJsonData[FIRSTNAME_INTER_JSON_FIELD_NAME],
                dateTimeInter: localDateTime,
                contract: ContractEnum.valueOfName(
                        strJsonData[CONTRACT_INTER_JSON_FIELD_NAME] == PASSAGE_DE_CABLE ?
                                CABLE_ROUTING.name() : strJsonData[CONTRACT_INTER_JSON_FIELD_NAME]),
                typeInter: TypeInterEnum.valueOfName(strJsonData[TYPE_INTER_JSON_FIELD_NAME]))
    }

    File getJsonFile() {
        new File(getUserHomePath() +
                getSeparator() +
                this.homeDirectoryName +
                getSeparator() +
                this.jsonBackupFileName)
    }

    void deleteHomeDataDirectory() {
        File file = new File(getUserHomePath() +
                getSeparator() +
                homeDirectoryName)
        if (file.exists()) {
            if (file.isDirectory()) file.deleteDir()
            if (file.isFile()) file.delete()
        }
    }

    void createHomeDataDirectory() {
        File file = new File(getUserHomePath() +
                getSeparator() +
                homeDirectoryName)
        if (file.exists()) {
            if (file.isFile()) {
                file.delete()
                file.mkdir()
            }
        } else file.mkdir()
    }

    void deleteJsonFile() {
        deleteHomeDataDirectory()
    }

    void createJsonFile() {
        createHomeDataDirectory()
        File file = new File(getUserHomePath() +
                getSeparator() +
                homeDirectoryName +
                getSeparator() +
                this.jsonBackupFileName
        )
        if (!(file.exists() && file.isFile()))
            if (file.exists() && file.isDirectory()) {
                file.deleteDir()
                file.createNewFile()
            } else file.createNewFile()
    }

    void createJsonFileAsDirectory() {
        createHomeDataDirectory()
        new File(getUserHomePath() +
                getSeparator() +
                homeDirectoryName +
                getSeparator() +
                this.jsonBackupFileName).mkdir()
    }

    void jsonBackupFileAssertions() {
        assert this.getJsonFile().exists()
        assert this.getJsonFile().isFile()
        assert !this.getJsonFile().isDirectory()
    }

    List<List<Integer>> getAnneesMoisDistinct() {
        List list = []
        this.getAnneesMoisDistinctAsMap().each { Map<Integer, Integer> it ->
            assert it.size() == 1
            list.add([it.keySet().first(),
                      it.getAt(it.keySet().first())])
        }
        list
    }

    List<Map<Integer, Integer>> getAnneesMoisDistinctAsMap() {
        List<Map<Integer, Integer>> map = new ArrayList<Map<Integer, Integer>>()
        List<Map<Integer, Integer>> allMap = this.getAllMapDates()
        Map<Integer, Integer> lastFound = allMap.first()
        map.add(lastFound)
        allMap.each { Map<Integer, Integer> it ->
            if (it != lastFound && !map.contains(it)) {
                map.add(it)
                lastFound = it
            }
        }
        map
    }

    List<Map<Integer, Integer>> getAllMapDates() {
        List<Map<Integer, Integer>> dateMaps = new ArrayList<Map<Integer, Integer>>()
        this.getAllDates().each { LocalDate date ->
            dateMaps.add([(date.getMonthValue()): date.getYear()])
        }
        dateMaps
    }

    List<LocalDate> getAllDates() {
        List<LocalDate> dates = new ArrayList<LocalDate>()
        this.getJsonData().each { Map<String, String> it ->
            dates.add LocalDate.parse(
                    it[DATE_INTER_JSON_FIELD_NAME],
                    DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT))
        }
        dates
    }

    List<Map<String, Integer>> getMoisFormatFrParAnnee() {
        List<List<Integer>> anneesMoisData = getAnneesMoisDistinct()
        List<Map<String, Integer>> finalResult =
                new ArrayList<Map<String, Integer>>(anneesMoisData.size())
        anneesMoisData.eachWithIndex { item, idx ->
            Integer intMois = item.get 0
            Integer annee = item.get 1
            Map<String, Integer> map = new HashMap<String, Integer>(1)
            map[ApplicationUtils.convertNombreEnMois(intMois)] = annee
            finalResult.add idx, map
        }
        finalResult
    }

    String getJsonBackupFilePath() {
        getUserHomePath() +
                getSeparator() +
                this.homeDirectoryName +
                getSeparator() +
                this.jsonBackupFileName
    }

    private File getJsonBackupFiletoArchived() {
        String ajout = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date())
        def jsonFilePath = this.getJsonBackupFilePath()
        def filePathWitoutExtension = FilenameUtils
                .removeExtension(jsonFilePath)
        String jsonBackUpFilename =
                "${filePathWitoutExtension}${ajout}.json"
        new File(jsonBackUpFilename)
    }

    @Test
    @Order(1)
    void testGetJsonBackupFilePath_json_file_does_not_exists() {
        deleteHomeDataDirectory()
        assert !this.getJsonFile().exists()
        interDataService.getJsonBackupFilePath()
        jsonBackupFileAssertions()
    }

    @Test
    @Order(2)
    void testGetJsonBackupFilePath_json_file_exists_as_directory() {
        deleteJsonFile()
        createJsonFileAsDirectory()
        assert this.getJsonFile().isDirectory()
        interDataService.getJsonBackupFilePath()
        jsonBackupFileAssertions()
    }

    @Test
    @Order(3)
    void testGetJsonBackupFilePath_json_file_exists() {
        createJsonFile()
        jsonBackupFileAssertions()
        interDataService.getJsonBackupFilePath()
        jsonBackupFileAssertions()
    }

    @Test
    @Order(4)
    void testFind_withNdAndType_not_found_inter() {
        Assertions.assertThrows(
                InterNotFoundException as Class<Throwable>,
                { ->
                    interDataService.find(
                            "0101010101",
                            TypeInterEnum.BAOC.name())
                })
    }

    @Test
    @Order(5)
    void testFind_withNdAndType_invalid_type() {
        Assertions.assertThrows(
                InterTypeEnumException as Class<Throwable>,
                { ->
                    assert interDataService.find(
                            "0144639035",
                            "bar")
                })
    }

    @Test
    @Order(6)
    void testFind_withNdAndType_valid_values() {
        Optional<InterEntity> premier = interRepository.findById(1L)
        reflectionEquals(premier.get().toDto(),
                interDataService.find(
                        premier.get().nd,
                        premier.get().typeInter.name()))
    }

    @Test
    @Order(7)
    @DisplayName('testCountMois')
    void testCountMois() {
        assert this.getAnneesMoisDistinct()
                .size() == interDataService.countMois()
    }

    @Test
    @Order(8)
    @DisplayName('testFindAllMoisFormatFrParAnnee')
    void testFindAllMoisFormatFrParAnnee() {
        List<Map<String, Integer>> expectedResult = this.getMoisFormatFrParAnnee()
        println expectedResult.toListString()
        println interDataService.findAllMoisFormatFrParAnnee().toListString()
        interDataService.findAllMoisFormatFrParAnnee()
                .eachWithIndex { Map<String, Integer> entry, int i ->
                    assert Maps.difference(
                            expectedResult.get(i), entry)
                            .areEqual()
//same test first with framework second without
                    assert entry.keySet().first() ==
                            expectedResult.get(i).keySet().first()
                    assert entry.get(entry.keySet().first()) ==
                            expectedResult.get(i)
                                    .get(expectedResult.get(i)
                                            .keySet().first())
                }

    }

    @Test
    @Order(9)
    @DisplayName('testGetFiberJsonFilePath_path')
    void testGetFiberJsonFilePath_path() {
        String expectedPath = getUserHomePath() +
                getSeparator() +
                this.homeDirectoryName +
                getSeparator() +
                this.jsonBackupFileName

        assert expectedPath ==
                interDataService.getJsonBackupFilePath()
    }

    @Test
    @Order(10)
    @Rollback
    @Transactional
    @DisplayName('testImportJsonFromFile')
    void testImportJsonFromFile() {
        truncateDB()//vider la base avant le test
        assert 0 == interRepository.count().toInteger()
//        String path = jsonDatasetPath
        assert jsonFileResource.exists()
        Object jsonInters = new JsonSlurper().parse jsonFileResource.getFile()
        interDataService.importJsonFromFile()
        assert interRepository.count() == (jsonInters as List<Map<String, String>>).size()
        //test si toutes les données sont conformes
        List<Map<String, String>> expectedData = this.getJsonData()
        interRepository.findAll(
                Sort.by("id")
                        .ascending())
                .eachWithIndex { inter, i ->
                    assert (TestUtils
                            .mapToInter(expectedData
                                    .get(i))
                            .equals(inter))
                }
    }

    @Test
    @Order(11)
    @DisplayName('testBuildJsonInter_withInter')
    void testBuildJsonInter_withInter() {
        String expectedJson = "{\"id_inter\":\"1\", " +
                "\"ND\":\"0144639035\", \"nom\":\"Lalande\"," +
                " \"prenom\":\"Julien\", \"heure\":\"10:00:00\"," +
                " \"date\":\"2018-10-29\", \"contrat\":\"IQ\"," +
                " \"type\":\"BAOC\"}"
        String resultJson = interDataService
                .buildJsonInter(interRepository
                        .findById(1L).get().toDto())
        JSONAssert.assertEquals(
                expectedJson,
                resultJson,
                true)
    }

    @Test
    @Order(12)
    @DisplayName('testSaveToJsonFile')
    void testSaveToJsonFile() {
        File jsonDataFile = getJsonFile()
        assert jsonDataFile.exists()
        assert !jsonDataFile.directory
        interDataService.saveToJsonFile()
        File jsonBackupFile = getJsonBackupFiletoArchived()
        assert jsonBackupFile.exists()
        assert !jsonBackupFile.directory
        assert jsonBackupFile.name.contains(
                FilenameUtils.removeExtension(getJsonFile().name))
        assert !getJsonFile().name.contains(
                FilenameUtils.removeExtension(jsonBackupFile.name))
    }

}
