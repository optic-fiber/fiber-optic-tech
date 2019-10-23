package com.cheroliv.opticfiber.config

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@Slf4j
@SpringBootTest(webEnvironment = NONE)
@DisplayName('ApplicationPropertiesIntegrationTest')
class ApplicationPropertiesIntegrationTest {
    @Autowired
    ApplicationContext applicationContext
    @Value('${application.data.recap-spreadsheet-file-name}')
    String spreadsheetFileName
    @Value('${application.data.home-directory-name}')
    String homeDirectoryName
    @Value('${application.data.json-backup-file-name}')
    String jsonBackupFileName

    Properties getApplicationProperties() {
        Resource propFileRsrc = applicationContext
                .getResource('classpath:application.properties')
        assert propFileRsrc.file, 'Requested resource is not a file\nla resource demandé n\'est pas un fichier'
        File propFile = propFileRsrc.getFile()
        Properties appProperties = new Properties()
        propFile.withInputStream { InputStream it ->
            appProperties.load(it)
        }
        appProperties
    }

    static boolean verifyProperty(Properties propertiesFromFile,
                                  String injectedString,
                                  String key) {
        propertiesFromFile.containsValue(injectedString) &&
                propertiesFromFile.containsKey(key) &&
                propertiesFromFile[key] == injectedString
    }

    @Test
    @DisplayName('applicationPropertiesLoads()')
    void applicationPropertiesLoads() {
        Properties propertiesFromFile = this.getApplicationProperties()
        assert verifyProperty(
                propertiesFromFile,
                this.spreadsheetFileName,
                'application.data.recap-spreadsheet-file-name')
        assert verifyProperty(
                propertiesFromFile,
                this.homeDirectoryName,
                'application.data.home-directory-name')
        assert verifyProperty(
                propertiesFromFile,
                this.jsonBackupFileName,
                'application.data.json-backup-file-name')
    }
}