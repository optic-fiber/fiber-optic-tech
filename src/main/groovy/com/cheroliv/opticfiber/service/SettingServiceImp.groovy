package com.cheroliv.opticfiber.service

import com.cheroliv.opticfiber.recap.service.RecapSettingService
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import static com.cheroliv.opticfiber.config.ApplicationConstants.*

@Slf4j
@Service
@TypeChecked
class SettingServiceImp implements SettingService {
    final String homeDirectoryName
    final RecapSettingService recapSettingService

    SettingServiceImp(@Value('${application.data.home-directory-name}')
                              String homeDirectoryName,
                      RecapSettingService recapSettingService) {
        this.homeDirectoryName = homeDirectoryName
        this.recapSettingService = recapSettingService
    }

    private String getDataHomeDirectoryPath() {
        System.getProperty(KEY_SYSTEM_PROPERTY_USER_HOME) +
                System.getProperty(KEY_SYSTEM_PROPERTY_FILE_SEPARATOR) +
                homeDirectoryName
    }

    @Override
    boolean isAppDataHomeDirectoryExists() {
        log.info("${this.class.simpleName}.isAppDataHomeDirectoryExists()")
        File file = new File(getDataHomeDirectoryPath())
        file.exists() && file.isDirectory() && !file.isFile()
    }


    @Override
    void createAppDataHomeDirectory() {
        log.info("${this.class.simpleName}.createAppDataHomeDirectory()")
        File dir = new File(getDataHomeDirectoryPath())
        assert !dir.exists()
        dir.mkdir()
        dataHomeDirectoryAssertions()
    }

    private void deleteAppDataHomeDirectoryIfExistsAsFile() {
        File file = new File(getDataHomeDirectoryPath())
        if (file.exists()) {
            if (file.isFile()) assert file.delete()
            else assert !file.isFile() && file.isDirectory()
        }
    }

    private void dataHomeDirectoryAssertions() {
        File file = new File(getDataHomeDirectoryPath())
        assert file.exists()
        assert file.isDirectory()
        assert !file.isFile()
    }

    @Override
    void settingUpApp() {
        log.info "${this.class.simpleName}.settingUpApp()"
        deleteAppDataHomeDirectoryIfExistsAsFile()
        isAppDataHomeDirectoryExists() ?
                log.info("appDataHomeDirectoryExists : true") :
                createAppDataHomeDirectory()
        recapSettingService.init()
    }
}
