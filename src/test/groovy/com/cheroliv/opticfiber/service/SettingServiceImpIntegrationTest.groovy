package com.cheroliv.opticfiber.service


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
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@Slf4j
@TypeChecked
@TestMethodOrder(OrderAnnotation)
@SpringBootTest(webEnvironment = NONE)
@DisplayName("SettingServiceImpIntegrationTest")
class SettingServiceImpIntegrationTest {

    @Autowired
    SettingService settingService
    @Value('${application.data.home-directory-name}')
    String homeDirectoryName
    @Value('${application.data.json-backup-file-name}')
    String jsonBackupFileName

    private String getExpectedDataHomeDirectoryPath() {
        getUserHomePath() +
                getSeparator() +
                homeDirectoryName
    }

    private void deleteFileCreateDirectory() {
        File file = new File(this.getExpectedDataHomeDirectoryPath())
        file.delete()
        file.mkdir()
    }

    private void createTestHomeDirectory() {
        log.info("${this.class.simpleName}.createTestHomeDirectory()")

        File file = new File(this.getExpectedDataHomeDirectoryPath())

        if (file.exists() &&
                !(file.isDirectory() && !file.isFile()))
            this.deleteFileCreateDirectory()
        else file.mkdir()
        assert file.exists()
        assert file.isDirectory()
        assert !file.isFile()
    }

    private void deleteTestHomeDirectory() {
        log.info("${this.class.simpleName}.deleteTestHomeDirectory()")
        File file = new File(this.getExpectedDataHomeDirectoryPath())
        if (file.exists())
            if (file.directory) file.deleteDir()

        assert !(file.directory && file.exists())
    }

    private void deleteTestHomeDirectoryAsFile() {
        log.info("${this.class.simpleName}.deleteTestHomeDirectoryAsFile()")
        File file = new File(this.getExpectedDataHomeDirectoryPath())
        if (file.exists())
            if (file.file) file.delete()

        assert !file.exists()
    }

    private void deleteTestHomeDirectoryAndFiles() {
        log.info("${this.class.simpleName}.deleteTestHomeDirectoryAndFiles()")
        this.deleteTestHomeDirectory()
        this.deleteTestHomeDirectoryAsFile()
        assert !new File(this.getExpectedDataHomeDirectoryPath()).exists()
    }

    private void createTestHomeDirectoryAsFile() {
        log.info("${this.class.simpleName}.createTestHomeDirectoryAsFile()")
        File file = new File(this.getExpectedDataHomeDirectoryPath())
        if (file.exists())
            if (file.isDirectory()) {
                assert file.deleteDir()
                assert file.createNewFile()
            } else assert file.isFile()
        else assert file.createNewFile()

        assert !file.exists() ?: file.isFile() && !file.isDirectory()
    }


    @Test
    @Order(1)
    @DisplayName("testIsAppDataHomeDirExists_exists_isDirectory")
    void testIsAppDataHomeDirExists_exists_isDirectory() {
        this.createTestHomeDirectory()
        File expectedDataHomeDirectoryFile =
                new File(this.getExpectedDataHomeDirectoryPath())
        assert expectedDataHomeDirectoryFile.exists()
        assert expectedDataHomeDirectoryFile.directory
        assert !expectedDataHomeDirectoryFile.file
        assert this.settingService.isAppDataHomeDirectoryExists()
    }


    @Test
    @Order(2)
    @DisplayName("testIsAppDataHomeDirExists_directory_not_exists")
    void testIsAppDataHomeDirExists_directory_not_exists() {
        this.deleteTestHomeDirectory()
        def file = new File(this.getExpectedDataHomeDirectoryPath())
        assert !(file.exists() && file.directory)
        assert !this.settingService.isAppDataHomeDirectoryExists()
    }


    @Test
    @Order(3)
    @DisplayName("testIsAppDataHomeDirExists_is_file")
    void testIsAppDataHomeDirExists_is_file() {
        this.deleteTestHomeDirectoryAndFiles()
        this.createTestHomeDirectoryAsFile()
        File file = new File(this.getExpectedDataHomeDirectoryPath())
        assert file.exists()
        assert file.isFile()
        assert !file.isDirectory()
        assert !this.settingService.isAppDataHomeDirectoryExists()
        // clean up after test
        this.deleteTestHomeDirectoryAsFile()
        assert !file.exists()
    }


    @Test
    @Order(4)
    @DisplayName("testCreateAppDataHomeDir")
    void testCreateAppDataHomeDir() {
        this.deleteTestHomeDirectoryAndFiles()
        File file = new File(this.getExpectedDataHomeDirectoryPath())
        assert !file.exists()
        this.settingService.createAppDataHomeDirectory()
        assert file.exists()
        assert file.isDirectory()
        assert !file.isFile()
    }


    @Test
    @Order(5)
    @DisplayName("testSettingUpApp_homeDataDirectory_isFile")
    void testSettingUpApp_homeDataDirectory_isFile() {
        this.createTestHomeDirectoryAsFile()
        File file = new File(this.getExpectedDataHomeDirectoryPath())
        assert file.exists()
        assert file.isFile()
        assert !file.isDirectory()
        this.settingService.settingUpApp()
        assert file.exists()
        assert !file.isFile()
        assert file.isDirectory()
    }


    @Test
    @Order(6)
    @DisplayName("testSettingUpApp_dir_exists")
    void testSettingUpApp_dir_exists() {
        this.deleteTestHomeDirectoryAndFiles()
        this.createTestHomeDirectory()
        File file = new File(this.getExpectedDataHomeDirectoryPath())
        assert file.exists()
        assert file.isDirectory()
        assert !file.isFile()
        this.settingService.settingUpApp()
        assert file.exists()
        assert file.isDirectory()
        assert !file.isFile()
    }


    @Test
    @Order(7)
    @DisplayName("testSettingUpApp_dir_not_exists")
    void testSettingUpApp_dir_not_exists() {
        this.deleteTestHomeDirectoryAndFiles()
        File file = new File(this.getExpectedDataHomeDirectoryPath())
        assert !file.exists()
        this.settingService.settingUpApp()
        assert file.exists()
        assert file.isDirectory()
        assert !file.isFile()
    }
}