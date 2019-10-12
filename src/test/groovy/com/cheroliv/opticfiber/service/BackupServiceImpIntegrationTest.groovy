package com.cheroliv.opticfiber.service

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate

//import org.springframework.test.jdbc.JdbcTestUtils

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(OrderAnnotation)
@DisplayName("BackupServiceImpIntegrationTest")
class BackupServiceImpIntegrationTest {
    final String COUNT_QUERY = 'select count(*) from "inter"'

    @Autowired
    JdbcTemplate jdbcTemplate
    @Autowired
    BackupService backupService
    @Value('${application.data.home-directory-name}')
    String homeDirectoryName
    @Value('${application.data.json-backup-file-name}')
    String jsonBackupFileName

    private String getJsonBackupFilePath() {
        System.getProperty('user.home') +
                System.getProperty("file.separator") +
                this.homeDirectoryName +
                System.getProperty("file.separator") +
                this.jsonBackupFileName
    }

    private String getDataHomeDirectoryFilePath() {
        System.getProperty('user.home') +
                System.getProperty("file.separator") +
                this.homeDirectoryName
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
        createDataHomeDirectory()
        File file = new File(this.getJsonBackupFilePath())
        if (!file.exists()) file.createNewFile()
        else if (file.isDirectory()) {
            file.deleteDir()
            file.createNewFile()
        }
    }

    private void createDataHomeDirectory() {
        File file = new File(getDataHomeDirectoryFilePath())
        if (!file.exists()) file.mkdir()
        else if (file.isFile()) {
            file.delete()
            file.mkdir()
        }
    }

    private void createJsonDirectory() {
        createDataHomeDirectory()
        File file = new File(getJsonBackupFilePath())
        if (!file.exists()) file.mkdir()
        else if (file.isFile()) {
            file.delete()
            file.mkdir()
        }
    }

    @Test
    @Order(1)
    @DisplayName("testIsDataBackupFileExists_not_exists")
    void testIsDataBackupFileExists_not_exists() {
        File file = new File(getJsonBackupFilePath())
        if (file.exists()) deleteJsonDirectoryAndFile()
        assert !file.exists()
        assert !backupService.isDataBackupFileExists()
    }

    @Test
    @Order(2)
    @DisplayName("testIsDataBackupFileExists_directory_exists")
    void testIsDataBackupFileExists_directory_exists() {
        createJsonDirectory()
        File file = new File(getJsonBackupFilePath())
        assert file.exists()
        assert file.isDirectory()
        assert !file.isFile()
        assert !backupService.isDataBackupFileExists()
    }

    @Test
    @Order(3)
    @DisplayName("testIsDataBackupFileExists_file_exists")
    void testIsDataBackupFileExists_file_exists() {
        File file = new File(this.getJsonBackupFilePath())
        if (file.exists()) {
            if (file.isDirectory()) {
                this.deleteJsonDirectory()
                this.createJsonFile()
            } else assert file.isFile()
        } else this.createJsonFile()
        assert file.exists()
        assert !file.isDirectory()
        assert file.isFile()
        assert this.backupService.isDataBackupFileExists()
    }


    @Test
    @Order(4)
    @DisplayName("testCreateDataBackupFile_notExists")
    void testCreateDataBackupFile_notExists() {
        this.deleteJsonDirectoryAndFile()
        File file = new File(this.getJsonBackupFilePath())
        assert !file.exists()
        this.backupService.createDataBackupFile()
        assert file.exists()
        assert file.isFile()
        assert !file.isDirectory()
    }

    @Test
    @Order(5)
    @DisplayName("testCreateDataBackupFile_exists_as_directory")
    void testCreateDataBackupFile_exists_as_directory() {
        this.deleteJsonfile()
        this.createJsonDirectory()
        File file = new File(this.getJsonBackupFilePath())
        assert file.exists() && file.isDirectory() && !file.isFile()
        backupService.createDataBackupFile()
        assert file.exists()
        assert file.isFile()
        assert !file.isDirectory()
    }

    @Test
    @Order(6)
    @DisplayName("testCreateDataBackupFile_exists_as_file")
    void testCreateDataBackupFile_exists_as_file() {
        this.deleteJsonDirectory()
        this.createJsonFile()
        File file = new File(this.getJsonBackupFilePath())
        assert file.exists() && !file.isDirectory() && file.isFile()
        this.backupService.createDataBackupFile()
        assert file.exists()
        assert file.isFile()
        assert !file.isDirectory()
    }

    /**
     * the goal of this test is only insure
     * environement is in right state
     * to be called by other class for other
     * works
     * Simply be sure we have the home data directory
     * and the json file to backup
     */
    @Test
    @Order(7)
    @DisplayName("testLoadBackupInDatabase")
    void testLoadBackupInDatabase_file_exists() {
        this.createJsonFile()
        File file = new File(this.getJsonBackupFilePath())
        assert file.exists()
        assert file.isFile()
        this.backupService.loadBackupInDatabase()
        assert file.exists(), "${this.getJsonBackupFilePath()} is available for the system"
        assert file.isFile()
    }

    /**
     * the goal of this test is only insure
     * environement is in right state
     * to be called by other class for other
     * works
     * Simply be sure we have the home data directory
     * and the json file to backup
     */
    @Test
    @Order(8)
    @DisplayName("testLoadBackupInDatabase")
    void testLoadBackupInDatabase_file_exists_as_directory() {
        this.createJsonDirectory()
        File file = new File(this.getJsonBackupFilePath())
        assert file.exists()
        assert file.isDirectory()
        this.backupService.loadBackupInDatabase()
        assert file.exists(), "${this.getJsonBackupFilePath()} is available for the system"
        assert file.isFile()
    }

    /**
     * the goal of this test is only insure
     * environement is in right state
     * to be called by other class for other
     * works
     * Simply be sure we have the home data directory
     * and the json file to backup
     */
    @Test
    @Order(9)
    @DisplayName("testLoadBackupInDatabase")
    void testLoadBackupInDatabase_file_not_exists() {
        this.deleteJsonDirectoryAndFile()
        File file = new File(this.getJsonBackupFilePath())
        assert !file.exists()
        this.backupService.loadBackupInDatabase()
        assert file.exists(), "${this.getJsonBackupFilePath()} is available for the system"
        assert file.isFile()
    }

}
