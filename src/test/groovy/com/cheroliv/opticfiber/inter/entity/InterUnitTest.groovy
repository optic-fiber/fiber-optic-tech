package com.cheroliv.opticfiber.inter.entity

import com.cheroliv.opticfiber.config.InterConstants
import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import groovy.json.JsonSlurper
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.springframework.core.io.ClassPathResource

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import java.nio.charset.StandardCharsets

import static com.cheroliv.opticfiber.config.InterConstants.ND_NOTNULL_CSTRT_TPL_MSG

//import com.cheroliv.fiber.inter.domain.enumeration.TypeInterEnum
//import org.junit.jupiter.api.MethodOrderer
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import java.time.LocalDate
//import java.time.LocalTime
//import static java.lang.Long.parseLong

import static com.xlson.groovycsv.CsvParser.parseCsv

@Slf4j
@TypeChecked
@TestMethodOrder(OrderAnnotation)
class InterUnitTest {

    static Integer interFieldMapSize = 7
    static Validator validator

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation
                .buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    //Recupere les données du fichier CSV dans un iterator
    static Iterator getCsvData() {
        parseCsv(new ClassPathResource('classpath:inter.csv')
                .getFile()
                .getText(StandardCharsets.UTF_8.name()),
                separator: ',',
                readFirstLine: false)
    }

    static List<Map<String, String>> getJsonData() {
        new JsonSlurper().parseText(
                new ClassPathResource('classpath:inters.json')
                        .file
                        .getText('utf-8')
        ) as List<Map<String, String>>
    }


    @Test
    @Order(1)
    @DisplayName('testNdNotNullConstraint')
    void testNdNotNullConstraint() {
        InterEntity inter = new InterEntity()
        Set<ConstraintViolation<InterEntity>> constraintViolations =
                validator.validateProperty(
                        inter,
                        "nd")
        assert constraintViolations
                .iterator()
                .next()
                .messageTemplate ==
                ND_NOTNULL_CSTRT_TPL_MSG

        inter.nd = "0101010101"
        constraintViolations =
                validator.validateProperty(
                        inter,
                        "nd")
        assert constraintViolations.size() == 0
    }

    @Test
    @Order(2)
    @DisplayName('testNdSizeConstraint')
    void testNdSizeConstraint() {
        InterEntity inter = new InterEntity(nd: "101010101")
        assert inter.nd.size() != 10
        Set<ConstraintViolation<InterEntity>> constraintViolations =
                validator.validateProperty inter, "nd"
        assert constraintViolations
                .iterator()
                .next()
                .messageTemplate ==
                InterConstants.ND_SIZE_CSTRT_TPL_MSG

        inter.nd = "0101010101"
        assert inter.nd.size() == 10
        constraintViolations =
                validator.validateProperty inter, "nd"
        assert constraintViolations.size() == 0
    }


    @Test
    @Order(3)
    void testTypeNotNullConstraint() {
        InterEntity inter = new InterEntity()
        Set<ConstraintViolation<InterEntity>> constraintViolations =
                validator.validateProperty inter, "typeInter"
        assert constraintViolations
                .iterator()
                .next()
                .messageTemplate ==
                InterConstants.NOT_NULL_CSTRT_TEMPLATE_MSG

        inter.typeInter = TypeInterEnum.valueOfName("BAAP")
        constraintViolations =
                validator.validateProperty inter, "typeInter"
        assert constraintViolations.size() == 0
    }


    @Test
    @Order(4)
    void testContratNotNullConstraint() {
        InterEntity inter = new InterEntity()
        Set<ConstraintViolation<InterEntity>> constraintViolations =
                validator.validateProperty inter, "contract"
        assert constraintViolations
                .iterator()
                .next()
                .messageTemplate,
                InterConstants.NOT_NULL_CSTRT_TEMPLATE_MSG

        inter.contract = ContractEnum.LM
        constraintViolations = validator.validateProperty inter, "contract"
        assert constraintViolations.size() == 0
    }

    @Test
    @Order(5)
    void testContratPatternConstraint() {
        InterEntity inter = new InterEntity(contract: ContractEnum.LM)
        Set<ConstraintViolation<InterEntity>> constraintViolations =
                validator.validateProperty inter, "contract"
        assert constraintViolations.size() == 0
        inter.contract = ContractEnum.IQ
        constraintViolations =
                validator.validateProperty inter, "contract"
        assert constraintViolations.size() == 0
        inter.contract = ContractEnum.CABLE_ROUTING
        constraintViolations =
                validator.validateProperty inter, "contract"
        assert constraintViolations.size() == 0
    }

    @Test
    @Order(6)
    void testPrenomSizeConstraint() {
        String prenom = ""
        for (int i = 0; prenom.size() <= InterConstants.PRENOM_SIZE_VALUE; i++) {
            prenom = prenom + i.toString()
        }
        assert !(prenom.size() <= InterConstants.PRENOM_SIZE_VALUE)
        InterEntity inter = new InterEntity(firstNameClient: prenom)
        Set<ConstraintViolation<InterEntity>> constraintViolations =
                validator.validateProperty inter, "firstNameClient"
        assert constraintViolations
                .iterator()
                .next()
                .messageTemplate ==
                InterConstants.SIZE_CSTRT_TEMPLATE_MSG

        inter.firstNameClient = "John"
        constraintViolations =
                validator.validateProperty inter, "firstNameClient"
        assert constraintViolations.size() == 0
    }

    @Test
    @Order(7)
    void testNomSizeConstraint() {
        String firstNameClient = ""
        for (int i = 0; firstNameClient.size() <=
                InterConstants.NOM_SIZE_VALUE; i++) {
            firstNameClient = firstNameClient + i.toString()
        }
        assert !(firstNameClient.size() <=
                InterConstants.NOM_SIZE_VALUE)
        InterEntity inter = new InterEntity(firstNameClient: firstNameClient)
        Set<ConstraintViolation<InterEntity>> constraintViolations =
                validator.validateProperty inter,
                        "firstNameClient"
        assert constraintViolations
                .iterator()
                .next()
                .messageTemplate ==
                InterConstants.SIZE_CSTRT_TEMPLATE_MSG

        inter.firstNameClient = "Doe"
        constraintViolations =
                validator.validateProperty inter,
                        "firstNameClient"
        assert constraintViolations.size() == 0
    }
}
