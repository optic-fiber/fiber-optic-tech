package com.cheroliv.opticfiber.service


//import groovy.transform.TypeChecked
//import groovy.util.logging.Slf4j
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
//import org.junit.jupiter.api.Order
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestMethodOrder
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.boot.test.context.SpringBootTest
//
//import static com.cheroliv.opticfiber.util.ApplicationUtils.separator
//import static com.cheroliv.opticfiber.config.ApplicationConstants.KEY_SYSTEM_PROPERTY_USER_HOME
//import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
//
//
//@Slf4j
//@TypeChecked
//@TestMethodOrder(OrderAnnotation)
//@SpringBootTest(webEnvironment = NONE)
class RecapSettingServiceUnitTest {

//    @Autowired
//    RecapSettingService settingService
//    @Value('${application.data.home-directory-name}')
//    String homeDirectoryName
//    @Value('${application.data.recap-spreadsheet-directory-name}')
//    String recapSpreadsheetDirectoryName
//
//    String getHomeDataDirectoryPath() {
//        new File(System.getProperty(KEY_SYSTEM_PROPERTY_USER_HOME) +
//                getSeparator() +
//                homeDirectoryName).path
//    }
//
//    String getRecapSpreadsheetDirectoryPath() {
//        new File(getHomeDataDirectoryPath() +
//                getSeparator() +
//                recapSpreadsheetDirectoryName).path
//    }
//
//    void deleteHomeDataDirectory() {
//        File file = new File(getHomeDataDirectoryPath())
//        if (file.exists()) {
//            if (file.isDirectory()) file.deleteDir()
//            if (file.isFile()) file.delete()
//        }
//    }
//
//    void deleteRecapitulatifSpreadsheetDirectory() {
//        File file = new File(getRecapSpreadsheetDirectoryPath())
//        if (file.exists()) {
//            if (file.isDirectory()) file.deleteDir()
//            if (file.isFile()) file.delete()
//        }
//    }
//
//    void createHomeDataDirectory() {
//        File file = new File(getHomeDataDirectoryPath())
//        if (!file.exists()) file.mkdir()
//        else if (file.isFile()) {
//            file.delete()
//            file.mkdir()
//        }
//    }
//
//    void createRecapitulatifSpreadsheetDirectory() {
//        File file = new File(getRecapSpreadsheetDirectoryPath())
//        if (!file.exists()) file.mkdir()
//        else if (file.isFile()) {
//            file.delete()
//            file.mkdir()
//        }
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName('testCheckDataHomeDirectoryExists_not_exists')
//    void testCheckDataHomeDirectoryExists_not_exists() {
//        deleteHomeDataDirectory()
//        File file = new File(getHomeDataDirectoryPath())
//        assert !file.exists()
//        assert settingService.initDataHomeDirectory()
//        assert file.exists()
//        assert file.isDirectory()
//
//    }
//
//
//    @Test
//    @Order(2)
//    @DisplayName('testCheckDataHomeDirectoryExists_exists')
//    void testCheckDataHomeDirectoryExists_exists() {
//        createHomeDataDirectory()
//        File file = new File(getHomeDataDirectoryPath())
//        assert file.exists()
//        assert file.isDirectory()
//        assert settingService.initDataHomeDirectory()
//        assert file.exists()
//        assert file.isDirectory()
//    }
//
//    @Test
//    @Order(4)
//    @DisplayName('testCheckRecapitulatifSpreadsheetDirectoryExists_exists')
//    void testCheckRecapitulatifSpreadsheetDirectoryExists_exists() {
//        createRecapitulatifSpreadsheetDirectory()
//        File file = new File(getRecapSpreadsheetDirectoryPath())
//        assert file.exists()
//        assert file.isDirectory()
//        assert settingService.initRecapSpreadsheetDirectory()
//        assert file.exists()
//        assert file.isDirectory()
//    }
//
//
//    @Test
//    @Order(3)
//    @DisplayName('testCheckRecapitulatifSpreadsheetDirectoryExists_not_exists')
//    void testCheckRecapitulatifSpreadsheetDirectoryExists_not_exists() {
//        deleteRecapitulatifSpreadsheetDirectory()
//        File file = new File(getRecapSpreadsheetDirectoryPath())
//        assert !file.exists()
//        assert settingService.initRecapSpreadsheetDirectory()
//        assert file.exists()
//        assert file.isDirectory()
//    }

}
