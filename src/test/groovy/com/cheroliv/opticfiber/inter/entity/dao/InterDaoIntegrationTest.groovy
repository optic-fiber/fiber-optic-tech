package com.cheroliv.opticfiber.inter.entity.dao

import com.cheroliv.opticfiber.ApplicationUtils
import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.inter.entity.InterEntity
import groovy.json.JsonSlurper
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

import javax.validation.ConstraintViolation
import javax.validation.Validator
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.format.DateTimeFormatter

import static com.cheroliv.opticfiber.config.ApplicationConstants.DATE_PATTERN_FORMAT
import static com.cheroliv.opticfiber.config.InterConstants.*
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@Slf4j
@TypeChecked
@TestMethodOrder(OrderAnnotation)
@DisplayName('InterRepositoryIntegrationTest')
@SpringBootTest(webEnvironment = NONE)
class InterDaoIntegrationTest {
    @Autowired
    ApplicationContext applicationContext
    @Autowired
    InterDao interRepository
    @Autowired
    Validator validator
    @Value('${application.data.home-directory-name}')
    String homeDirectoryName
    @Value('${application.data.json-backup-file-name}')
    String jsonBackupFileName
    @Value("classpath:inters.json")
    Resource resourceFile

    @BeforeEach
    void setUp() { populateDB() }

    List<Map<String, String>> getJsonData() {
        new JsonSlurper()?.parseText(resourceFile
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
                                ContractEnum.CABLE_ROUTING.name() : strJsonData[CONTRACT_INTER_JSON_FIELD_NAME]),
                typeInter: TypeInterEnum.valueOfName(strJsonData[TYPE_INTER_JSON_FIELD_NAME]))
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
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
        dates
    }


    @Transactional
    void populateDB() {
        if (interRepository?.count() == 0)
            jsonData.each {
                Map<String, String> it ->
                    InterEntity inter = jsonDataToInter(it)
                    if (!validator.validate(inter).empty)
                        validator.validate(inter).each {
                            ConstraintViolation<InterEntity> cv ->
                                log.info cv.message
                                log.info inter.toString()
                        }
                    else interRepository.save inter
            }
    }

    @Transactional
    void truncateDB() {
        interRepository.deleteAll()
    }


    @Test
    @Order(1)
    @DisplayName('testFind_withNdAndType')
    void testFind_withNdAndType() {
        Map<String, String> expectedMap = this.getJsonData().first()
        InterEntity expectedInter = jsonDataToInter(expectedMap)
        println "\n\n\n\n$expectedMap"
        println expectedInter
        //because jsonData.first() then id must be 1 and
        // not expectedInter.id cant trust the id in json file
        // only can what the DB will generate
        Optional<InterEntity> optionalResultInter = interRepository
                .findById 1L//not expectedInter.id
        println optionalResultInter.get()
        if (optionalResultInter.present) {
            InterEntity resultInter = optionalResultInter.get()
            Optional<InterEntity> optionalInterResult = interRepository.find(
                    resultInter.nd, resultInter.typeInter)
            if (optionalInterResult.present) {
                InterEntity result = optionalInterResult.get()
                assert expectedInter.equals(result)
            } else throw new NoSuchElementException(
                    "No value present for find(${resultInter.nd}," +
                            " ${resultInter.typeInter.name()})")
        } else throw new NoSuchElementException(
                "No value present for findById(${expectedInter.id})")
    }


    @Test
    @Order(2)
    @DisplayName('testFindAllDeMoisDansAnnee_withMoisAndAnnee')
    void testFindAllDeMoisDansAnnee_withMoisAndAnnee() {
        Integer intAnnee = 2018
        Integer intMois = Month.OCTOBER.value
        List<InterEntity> expectedResult = new ArrayList<>()
        jsonData.each {
            LocalDate date = LocalDate.parse(
                    it[DATE_INTER_JSON_FIELD_NAME],
                    DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT))
            if (date.year == intAnnee &&
                    date.monthValue == intMois)
                expectedResult.add(jsonDataToInter(it))
        }
        List<InterEntity> result = interRepository
                .findAllDeMoisDansAnnee intMois, intAnnee
        assert expectedResult.size() == result.size()
        expectedResult.eachWithIndex {
            InterEntity entry, int i ->
                assert entry.equals(result.get(i))
        }
    }


    @Test
    @Order(3)
    @DisplayName('testDistinctMoisParAnnee')
    void testDistinctMoisParAnnee() {
        List<List<Integer>> expectedResult = this.getAnneesMoisDistinct()
        interRepository.distinctMoisParAnnee().eachWithIndex {
            List<Integer> list, int x ->
                list.eachWithIndex {
                    int integer, int y ->
                        assert expectedResult.get(x).get(y) == integer
                }
        }
    }


    @Test
    @Order(4)
    void testCountPlpParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = this.getJsonData()
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(
                        map[DATE_INTER_JSON_FIELD_NAME])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it[DATE_INTER_JSON_FIELD_NAME]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it[TYPE_INTER_JSON_FIELD_NAME] ==
                    TypeInterEnum.PLP.name()) {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countPlpParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(5)
    void testCountRacParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = this.getJsonData()
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    (it["type"] == "BAAP" ||
                            it["type"] == "BAOC" ||
                            it["type"] == "BAFA" ||
                            it["type"] == "BAST") &&
                    it["contrat"] != "Passage de cable") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countRacParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(6)
    void testCountInterParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue) {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countInterParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(7)
    void testCountBafaParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["type"] == "BAFA" &&
                    it["contrat"] != "Passage de cable") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countBafaParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(8)
    void testCountBastParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["type"] == "BAST" &&
                    it["contrat"] != "Passage de cable") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countBastParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }


    @Test
    @Order(9)
    void testCountSavParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> jsonMap = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(jsonMap["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["type"] == "SAV") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countSavParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(10)
    void testCountPdcParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["contrat"] == "Passage de cable") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countPdcParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(11)
    void testCountPdcBaapParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["contrat"] == "Passage de cable" &&
                    it["type"] == "BAAP") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countPdcBaapParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(12)
    void testCountPdcBaocParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["contrat"] == "Passage de cable" &&
                    it["type"] == "BAOC") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countPdcBaocParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(13)
    void testCountPdcBafaParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["contrat"] == "Passage de cable" &&
                    it["type"] == "BAFA") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countPdcBafaParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(14)
    void testCountPdcBastParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["contrat"] == "Passage de cable" &&
                    it["type"] == "BAST") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countPdcBastParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(15)
    void testCountBaocParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["type"] == "BAOC" &&
                    it["contrat"] != "Passage de cable") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countBaocParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(16)
    void testCountBaapParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["type"] == "BAAP" &&
                    it["contrat"] != "Passage de cable") {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countBaapParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }

    @Test
    @Order(17)
    void testCountPdcBaocBaapParMoisDansAnnee_withMoisAndAnnee() {
        List<Map<String, String>> data = jsonData
        Map<String, String> map = data.get(3)
        LocalDate dateExpected = ApplicationUtils
                .parseStringDateToLocalDate(map["date"])
        Integer expectedCount = 0
        data.each {
            LocalDate date = ApplicationUtils
                    .parseStringDateToLocalDate it["date"]
            if (date.year == dateExpected.year &&
                    date.monthValue == dateExpected.monthValue &&
                    it["contrat"] == "Passage de cable" &&
                    (it["type"] == "BAOC" ||
                            it["type"] == "BAAP")
            ) {
                expectedCount++
            }
        }
        assert expectedCount ==
                interRepository.countPdcBaocBaapParMoisDansAnnee(
                        dateExpected.monthValue,
                        dateExpected.year)
    }


    //pas utile de test le frmwrk Spring data
    //juste pour garder un exemple de rollback en test
    @Test
    @Order(18)
    @Rollback
    @Transactional
    void testSave() {
        Long countBefore = interRepository.count()
        def prePersistInstance = new InterEntity(
                nd: "0101010101",
                lastNameClient: "Doe",
                firstNameClient: "John",
                typeInter: TypeInterEnum.valueOfName("BAFA"),
                contract: ContractEnum.valueOfName("LM"),
                dateTimeInter: LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.now()))
        interRepository.save(prePersistInstance)
        Long countAfter = interRepository.count()
        assert countAfter == countBefore + 1
        //preuve que EntityManager.persist() affecte l'id
        // sur l'instance passé en argument
        // pas besoin de recuperer le resultat en retour de fonction
        assert prePersistInstance.id != null
    }
}
