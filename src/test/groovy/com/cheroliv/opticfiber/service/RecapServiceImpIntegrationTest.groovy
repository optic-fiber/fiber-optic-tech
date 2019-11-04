package com.cheroliv.opticfiber.service

import com.cheroliv.opticfiber.util.ApplicationUtils
import com.cheroliv.opticfiber.config.InterConstants
import com.cheroliv.opticfiber.domain.InterDto
import com.cheroliv.opticfiber.domain.enumerations.ContractEnum
import com.cheroliv.opticfiber.domain.enumerations.TypeInterEnum
import com.cheroliv.opticfiber.entity.InterEntity
import com.cheroliv.opticfiber.entity.dao.InterDao
import com.cheroliv.opticfiber.inter.service.InterDataService
import com.cheroliv.opticfiber.domain.Recap
import com.cheroliv.opticfiber.recap.service.RecapService
import com.cheroliv.opticfiber.view.SpreadsheetRecap
import com.google.common.collect.Maps
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource
import org.springframework.transaction.annotation.Transactional

import javax.validation.Validator
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.Month
import java.time.format.TextStyle

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@Slf4j
@CompileStatic@Disabled
@SpringBootTest(webEnvironment = NONE)
@TestMethodOrder(OrderAnnotation)
class RecapServiceImpIntegrationTest {
    @Autowired
    RecapService recapService
    @Autowired
    InterDataService interDataService
    @Autowired
    InterDao repo
    @Autowired
    ApplicationContext applicationContext
    @Autowired
    Validator validator
    @Value('classpath:inters.json')
    Resource jsonFileResource
    @Value('${application.data.home-directory-name}')
    String homeDirectoryName
    String classeurPathName = 'recap_date-time1_date-time2.xlsx'
    @Value('classpath:recap_date-time1_date-time2.xlsx')
    Resource classeurResource

    @BeforeEach
    void setUp() {
        populateDB()
    }

    @Transactional
    void populateDB() {
        if (repo.count() == 0)
            jsonData.each {
                InterEntity inter = jsonDataToInter(it)
                if (!validator.validate(inter).empty)
                    validator.validate(inter).each {
                        log.info it.message
                        log.info inter.toString()
                    }
                else repo.save inter
            }
    }

    static InterEntity jsonDataToInter(Map<String, String> strJsonData) {
        LocalDateTime localDateTime = LocalDateTime.of(
                ApplicationUtils.parseStringDateToLocalDate(strJsonData[InterConstants.DATE_INTER_JSON_FIELD_NAME]),
                ApplicationUtils.parseStringHeureToLocalTime(strJsonData[InterConstants.HOUR_INTER_JSON_FIELD_NAME]))

        new InterEntity(
                id: Long.parseLong(strJsonData[InterConstants.ID_INTER_JSON_FIELD_NAME]),
                nd: strJsonData[InterConstants.ND_INTER_JSON_FIELD_NAME],
                lastNameClient: strJsonData[InterConstants.LASTNAME_INTER_JSON_FIELD_NAME],
                firstNameClient: strJsonData[InterConstants.FIRSTNAME_INTER_JSON_FIELD_NAME],
                dateTimeInter: localDateTime,
                contract: ContractEnum.valueOfName(
                        strJsonData[InterConstants.CONTRACT_INTER_JSON_FIELD_NAME] == InterConstants.PASSAGE_DE_CABLE ?
                                ContractEnum.CABLE_ROUTING.name() : strJsonData[InterConstants.CONTRACT_INTER_JSON_FIELD_NAME]),
                typeInter: TypeInterEnum.valueOfName(strJsonData[InterConstants.TYPE_INTER_JSON_FIELD_NAME]))
    }

    List<Map<String, String>> getJsonData() {
        new JsonSlurper()?.parseText(jsonFileResource
                .file.getText(StandardCharsets.UTF_8.name())
        ) as List<Map<String, String>>
    }

    @Test
    @Order(1)
    void testNomFeuilles() {
        Integer countMois = interDataService.countMois()
        List<Map<String, Integer>> list = interDataService.findAllMoisFormatFrParAnnee()
        List<String> finalList = new ArrayList(countMois)
        list.eachWithIndex { item, idx ->
            String key = (item as Map<String, Integer>).keySet().first()
            String value = (item as Map<String, Integer>).get(key)
            String monthYearLabel = "$key $value"
            finalList.add idx, monthYearLabel
        }
        recapService.nomFeuilles().eachWithIndex {
            String str, int i ->
                assert finalList.get(i) == str
        }
    }

    @Test
    @Order(2)
    void testInit() {
//        recapService.path = homeDirectoryName
        Integer nbMois = interDataService.countMois()
        String fullClasseurPath = classeurResource.URI.path
        SpreadsheetRecap expectedClasseur = new SpreadsheetRecap(
                classeurPathName: fullClasseurPath,
                nbFeuille: nbMois,
                nomFeuilles: recapService.nomFeuilles(),
                moisParAnnee: interDataService.findAllMoisFormatFrParAnnee())
        SpreadsheetRecap resultRecap = recapService.init()
        assert expectedClasseur.classeurPathName ==
                resultRecap.classeurPathName
        assert expectedClasseur.nbFeuille ==
                resultRecap.nbFeuille
        expectedClasseur.nomFeuilles.eachWithIndex {
            String entry, int i ->
                assert entry == resultRecap.nomFeuilles.get(i)
        }
        expectedClasseur.moisParAnnee.eachWithIndex {
            Map<String, Integer> item, int idx ->
                assert Maps.difference(
                        expectedClasseur.moisParAnnee.get(idx),
                        resultRecap.moisParAnnee.get(idx))
                        .areEqual()
        }
    }

    @Test
    @Order(3)
    void testConstructRecap() {
        Integer anneeIntValue = 2018
        Integer moisInt = Month.DECEMBER.value
        String nomFeuille = "${Month.DECEMBER.getDisplayName(TextStyle.FULL, Locale.FRANCE)} ${anneeIntValue}"
        Recap expectedRecap = new Recap(
                sheetName: nomFeuille,
                inters: repo.findAllDeMoisDansAnnee(
                        moisInt, anneeIntValue).collect {
                    InterEntity it ->
                        it.toDto()
                },
                annee: anneeIntValue,
                mois: moisInt,
                nbInterTotal: repo
                        .countInterParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbBaocBaap: repo
                        .countRacParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbBafa: repo
                        .countBafaParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbBast: repo
                        .countBastParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPlp: repo
                        .countPlpParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbSav: repo
                        .countSavParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPdcTotal: repo
                        .countPdcParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPdcBafa: repo
                        .countPdcBafaParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPdcBast: repo
                        .countPdcBastParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPdcBaocBaap: repo
                        .countPdcBaocBaapParMoisDansAnnee(
                                moisInt, anneeIntValue),
                labelTitreRecap:
                        "${Recap.PRE_LABEL_TITRE_RECAP}" +
                                "${ApplicationUtils.convertNombreEnMois moisInt}" +
                                " $anneeIntValue",
                labelCurrentMonthYearFormattedFr:
                        ApplicationUtils.convertNombreEnMois(moisInt))

        Integer monthIdx = 2
        SpreadsheetRecap classeurResult = recapService.init()
        Recap resultRecap = recapService.processRecap(
                classeurResult.nomFeuilles.get(monthIdx),
                moisInt, anneeIntValue)
        assert expectedRecap.sheetName == resultRecap.sheetName
        expectedRecap.inters.eachWithIndex { InterDto entry, int i ->
            assert entry == resultRecap.inters.get(i)
        }
        assert expectedRecap.annee == resultRecap.annee
        assert expectedRecap.mois == resultRecap.mois
        assert expectedRecap.nbInterTotal == resultRecap.nbInterTotal
        assert expectedRecap.nbBafa == resultRecap.nbBafa
        assert expectedRecap.nbBaocBaap == resultRecap.nbBaocBaap
        assert expectedRecap.nbBast == resultRecap.nbBast
        assert expectedRecap.nbPlp == resultRecap.nbPlp
        assert expectedRecap.nbSav == resultRecap.nbSav
        assert expectedRecap.nbPdcTotal == resultRecap.nbPdcTotal
        assert expectedRecap.nbPdcBafa == resultRecap.nbPdcBafa
        assert expectedRecap.nbPdcBast == resultRecap.nbPdcBast
        assert expectedRecap.nbPdcBaocBaap == resultRecap.nbPdcBaocBaap
        assert expectedRecap.labelTitreRecap ==
                resultRecap.labelTitreRecap
        assert expectedRecap.labelCurrentMonthYearFormattedFr ==
                resultRecap.labelCurrentMonthYearFormattedFr
    }


    @Test
    @Order(4)
    void testProcessFeuille() {
        Integer nbMois = interDataService.countMois()
        SpreadsheetRecap expectedClasseur = new SpreadsheetRecap(
                classeurPathName: classeurResource.URI.path,
                nbFeuille: nbMois,
                nomFeuilles: recapService.nomFeuilles(),
                moisParAnnee: interDataService.findAllMoisFormatFrParAnnee())


        List<List<Integer>> listIntMoisAnnee =
                repo.distinctMoisParAnnee()
        expectedClasseur.recaps = new ArrayList<Recap>(expectedClasseur.nbFeuille)
        assert expectedClasseur.nbFeuille == expectedClasseur.moisParAnnee.size()
        assert expectedClasseur.nbFeuille == listIntMoisAnnee.size()

        if (expectedClasseur.nbFeuille == null || expectedClasseur.nbFeuille <= 0) {
            expectedClasseur.recaps = new ArrayList<Recap>()
            expectedClasseur.nbFeuille = 0
        } else {
            expectedClasseur.moisParAnnee.eachWithIndex {
                Map<String, Integer> map, int idx ->
                    String moisStrKey = map.keySet().first()
                    Integer anneeIntValue = map.get(moisStrKey)
                    Integer moisInt = listIntMoisAnnee.get(idx).first()
                    expectedClasseur.recaps.add idx,
                            new Recap(
                                    sheetName: expectedClasseur.nomFeuilles.get(idx),
                                    inters: repo.findAllDeMoisDansAnnee(
                                            moisInt, anneeIntValue)
                                            .collect {
                                                InterEntity it ->
//                                                    new InterDto(it)
                                                    it.toDto()
                                            },
                                    annee: anneeIntValue,
                                    mois: moisInt,
                                    nbInterTotal: repo
                                            .countInterParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    nbBaocBaap: repo
                                            .countRacParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    nbBafa: repo
                                            .countBafaParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    nbBast: repo
                                            .countBastParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    nbPlp: repo
                                            .countPlpParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    nbSav: repo
                                            .countSavParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    nbPdcTotal: repo
                                            .countPdcParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    nbPdcBafa: repo
                                            .countPdcBafaParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    nbPdcBast: repo
                                            .countPdcBastParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    nbPdcBaocBaap: repo
                                            .countPdcBaocBaapParMoisDansAnnee(
                                                    moisInt, anneeIntValue),
                                    labelTitreRecap:
                                            "${Recap.PRE_LABEL_TITRE_RECAP}" +
                                                    "${ApplicationUtils.convertNombreEnMois moisInt}" +
                                                    " $anneeIntValue",
                                    labelCurrentMonthYearFormattedFr:
                                            ApplicationUtils.convertNombreEnMois(moisInt))
            }
        }

//        recapService.path = homeDirectoryName
        SpreadsheetRecap resultClasseur = recapService.processFeuilles()

        expectedClasseur.nomFeuilles.eachWithIndex { String entry, int i ->
            assert entry == resultClasseur.nomFeuilles.get(i)
        }
        expectedClasseur.moisParAnnee.eachWithIndex {
            Map<String, Integer> entry, int i ->
                assert Maps.difference(
                        entry,
                        resultClasseur.moisParAnnee.get(i)
                ).areEqual()
        }
        assert expectedClasseur.nbFeuille == resultClasseur.nbFeuille
        assert expectedClasseur.classeurPathName == resultClasseur.classeurPathName
    }

    @Test
    @Order(5)
    void testProcessClasseurFeuilles() {
        String separator = System.getProperty('file.separator')
        File pwd = new File(applicationContext
                .getResource("/").URI)//pwd
        String baseFolderPath = pwd.path

        String pathFiberUserDataFolderName = baseFolderPath +
                separator +
                homeDirectoryName


        File pathFiberUserDataFolderNameDir =
                new File(pathFiberUserDataFolderName)

        pathFiberUserDataFolderNameDir.exists() &&
                pathFiberUserDataFolderNameDir.directory ?:
                pathFiberUserDataFolderNameDir.mkdir()//.fiber


        String pathOutput = baseFolderPath +
                separator +
                homeDirectoryName +
                separator +
                classeurResource.getFile().name


        File outputDir = new File(pathOutput)
        outputDir.exists() &&
                outputDir.directory ?:
                outputDir.mkdir()//output

        Resource resource = applicationContext
                .getResource("file:" +
                        "$pathOutput$separator" +
                        "$classeurPathName")
        !resource.exists() ?: resource.file.delete()

        assert !resource.exists()

        recapService.processClasseurFeuilles()//baseFolderPath)
        assert applicationContext
                .getResource("file:$pathOutput$separator" +
                        "$classeurPathName")
                .file
                .exists()
    }
}
