package com.cheroliv.opticfiber.service


import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import static com.cheroliv.opticfiber.util.ApplicationUtils.separator
import static com.cheroliv.opticfiber.util.ApplicationUtils.userHomePath

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
        userHomePath + separator + homeDirectoryName
    }

    @Override
    boolean isAppDataHomeDirectoryExists() {
        log.info("${this.class.simpleName}.isAppDataHomeDirectoryExists()")
        File file = new File(dataHomeDirectoryPath)
        file.exists() && file.directory && !file.file
    }


    @Override
    void createAppDataHomeDirectory() {
        log.info("${this.class.simpleName}.createAppDataHomeDirectory()")
        File dir = new File(dataHomeDirectoryPath)
        assert !dir.exists()
        dir.mkdir()
        dataHomeDirectoryAssertions()
    }

    private void deleteAppDataHomeDirectoryIfExistsAsFile() {
        File file = new File(dataHomeDirectoryPath)
        if (file.exists()) {
            if (file.file) assert file.delete()
            else assert !file.file && file.directory
        }
    }

    private void dataHomeDirectoryAssertions() {
        File file = new File(getDataHomeDirectoryPath())
        assert file.exists()
        assert file.directory
        assert !file.file
    }

    @Override
    void settingUpApp() {
        log.info "${this.class.simpleName}.settingUpApp()"
        deleteAppDataHomeDirectoryIfExistsAsFile()
        appDataHomeDirectoryExists ?
                log.info("appDataHomeDirectoryExists : true") :
                createAppDataHomeDirectory()
        recapSettingService.init()
    }
}
