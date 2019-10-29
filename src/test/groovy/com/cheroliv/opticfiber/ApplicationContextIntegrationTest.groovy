package com.cheroliv.opticfiber

import com.cheroliv.core.entity.dao.UserDao
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport

import javax.validation.Validator

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@Slf4j
@TypeChecked
@SpringBootTest(webEnvironment = NONE)
@ExtendWith(SpringExtension)
@TestMethodOrder(OrderAnnotation)
class ApplicationContextIntegrationTest {
    @Autowired
    Validator validator
    @Autowired
    ApplicationContext context


    @Test
    @Order(4)
    @DisplayName("testContextComponents")
    void testContextComponents() {
        log.info context.getBean(UserDao).count().toString()
    }


    @Test
    @Order(1)
    @DisplayName("Test Spring Context")
    void contextLoads() {
        log.info("context loaded")
    }

    @Test
    @Order(2)
    @DisplayName("Test Spring Validator injection")
    void testContextContainsValidator() {
        assert validator.class.name ==
                LocalValidatorFactoryBean.class.name
    }

    @Test
    @Disabled
    @Order(3)
    @DisplayName("Test Spring context contains SecurityProblemSupport")
    void testContextContainsSecurityProblemSupport() {
        assert SecurityProblemSupport.class.name ==
                context.getBean(SecurityProblemSupport).class.name
    }
}