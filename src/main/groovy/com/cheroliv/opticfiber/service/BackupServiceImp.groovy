package com.cheroliv.opticfiber.service


import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import static com.cheroliv.opticfiber.util.ApplicationUtils.getSeparator
import static com.cheroliv.opticfiber.util.ApplicationUtils.userHomePath

@Slf4j
@Service
@TypeChecked
class BackupServiceImp implements BackupService {

    final SettingService settingService
    final InterDataService interService
    final String homeDirectoryName
    final String jsonBackupFileName

    BackupServiceImp(SettingService settingService,
                     InterDataService interService,
                     @Value('${application.data.home-directory-name}')
                             String homeDirectoryName,
                     @Value('${application.data.json-backup-file-name}')
                             String jsonBackupFileName) {
        this.settingService = settingService
        this.interService = interService
        this.homeDirectoryName = homeDirectoryName
        this.jsonBackupFileName = jsonBackupFileName
    }

    private String getJsonBackupFilePath() {
        getUserHomePath() +
                getSeparator() +
                this.homeDirectoryName +
                getSeparator() +
                this.jsonBackupFileName
    }

    @Override
    Boolean isDataBackupFileExists() {
        log.info("${this.class.simpleName}.isDataBackupFileExists()")
        File file = new File(this.getJsonBackupFilePath())
        file.exists() && file.isFile() && !file.isDirectory()
    }

    void createDataBackupFile() {
        log.info("${this.class.simpleName}.createDataBackupFile()")
        File file = new File(this.getJsonBackupFilePath())
        if (file.exists()) {
            if (file.isDirectory()) {
                file.deleteDir()
                file.createNewFile()
            }
        } else file.createNewFile()
        assert file.exists()
        assert file.isFile()
        assert !file.isDirectory()
    }


    @Override
    void loadBackupInDatabase() {
        log.info("${this.class.simpleName}" +
                ".loadBackupInDatabase()")
        this.settingService.settingUpApp()
        this.isDataBackupFileExists() ?:
                this.createDataBackupFile()
        this.interService.importJsonFromFile(
        )
    }
}
