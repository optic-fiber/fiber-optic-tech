package com.cheroliv.opticfiber.config


import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

import static com.cheroliv.opticfiber.util.ApplicationUtils.separator
import static com.cheroliv.opticfiber.util.ApplicationUtils.userHomePath

@Slf4j
@TypeChecked
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(OrderAnnotation)
@DisplayName("FiberInitializerIntegrationTest")
class FiberInitializerIntegrationTest {

    @Autowired
    FiberInitializer fiberInitializer
    @Value('${application.data.home-directory-name}')
    String homeDirectoryName
    @Value('${application.data.json-backup-file-name}')
    String jsonBackupFileName

    private String getJsonBackupFilePath() {
        getUserHomePath() +
                getSeparator() +
                this.homeDirectoryName +
                getSeparator() +
                this.jsonBackupFileName
    }

    private void deleteJsonfile() {
        File file = new File(this.getJsonBackupFilePath())
        if (file.exists() && file.isFile()) file.delete()
    }

    private void deleteJsonDirectory() {
        File file = new File(this.getJsonBackupFilePath())
        if (file.exists() && file.isDirectory()) file.deleteDir()
    }

    private void deleteJsonDirectoryAndFile() {
        this.deleteJsonDirectory()
        this.deleteJsonfile()
    }

    private void createJsonFile() {
        File file = new File(this.getJsonBackupFilePath())
        if (!file.exists()) file.createNewFile()
        else if (file.isDirectory()) {
            file.deleteDir()
            file.createNewFile()
        }
    }

    private void createJsonDirectory() {
        File file = new File(this.getJsonBackupFilePath())
        if (!file.exists()) file.mkdir()
        else if (file.isFile()) {
            file.delete()
            file.mkdir()
        }
    }

    @Test
    @Order(1)
    @DisplayName("testAfterPropertiesSet_backupfile_exists")
    void testAfterPropertiesSet_backupfile_exists() {
        this.createJsonFile()
        File file = new File(this.getJsonBackupFilePath())
        assert file.exists() && file.isFile() && !file.isDirectory()
        this.fiberInitializer.afterPropertiesSet()
        assert file.exists() && file.isFile() && !file.isDirectory()
    }

    @Test
    @Order(2)
    @DisplayName("testAfterPropertiesSet_backupfile_not_exists")
    void testAfterPropertiesSet_backupfile_not_exists() {
        this.deleteJsonDirectoryAndFile()
        File file = new File(this.getJsonBackupFilePath())
        assert !file.exists()
        this.fiberInitializer.afterPropertiesSet()
        assert file.exists() && file.isFile() && !file.isDirectory()
    }

    @Test
    @Order(3)
    @DisplayName("testAfterPropertiesSet_backupfile_exists_as_directory")
    void testAfterPropertiesSet_backupfile_exists_as_directory() {
        this.createJsonDirectory()
        File file = new File(this.getJsonBackupFilePath())
        assert file.exists() && !file.isFile() && file.isDirectory()
        this.fiberInitializer.afterPropertiesSet()
        assert file.exists() && file.isFile() && !file.isDirectory()
    }
}

