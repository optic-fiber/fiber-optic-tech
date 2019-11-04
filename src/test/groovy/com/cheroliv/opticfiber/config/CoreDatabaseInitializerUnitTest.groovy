package com.cheroliv.opticfiber.config

import com.cheroliv.core.config.CoreDatabaseInitializer
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

import static org.mockito.MockitoAnnotations.initMocks


@Slf4j
@TypeChecked
@ExtendWith(MockitoExtension)
@TestMethodOrder(OrderAnnotation)
@DisplayName('CoreDatabaseInitializerUnitTest')
class CoreDatabaseInitializerUnitTest {
    @BeforeAll
    static void init() { initMocks(this) }
    @InjectMocks
    CoreDatabaseInitializer coreDatabaseInitializer
    TestData data = TestData.instance

    @Test
    @DisplayName('testInitializeDatabase')
    void testInitializeDatabase() {
        log.info('testInitializeDatabase()')
    }

    @Test
    @DisplayName('testCreateDefaultAuth')
    void testCreateDefaultAuth() {
        log.info('testCreateDefaultAuth()')
    }

    @Test
    @DisplayName('testCreateDefaultUsers')
    void testCreateDefaultUsers() {
        log.info('testCreateDefaultUsers()')
    }
}
