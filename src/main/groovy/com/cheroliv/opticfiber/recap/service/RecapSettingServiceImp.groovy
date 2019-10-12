package com.cheroliv.opticfiber.recap.service


import com.cheroliv.opticfiber.recap.service.exceptions.RecapSettingInitialisationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import static com.cheroliv.opticfiber.config.ApplicationConstants.KEY_SYSTEM_PROPERTY_FILE_SEPARATOR
import static com.cheroliv.opticfiber.config.ApplicationConstants.KEY_SYSTEM_PROPERTY_USER_HOME


@Service
class RecapSettingServiceImp implements RecapSettingService {

    final String homeDirectoryName
    final String recapSpreadsheetDirectoryName

    RecapSettingServiceImp(@Value('${application.data.home-directory-name}')
                                   String homeDirectoryName,
                           @Value('${application.data.recap-spreadsheet-directory-name}')
                                   String recapSpreadsheetDirectoryName) {
        this.homeDirectoryName = homeDirectoryName
        this.recapSpreadsheetDirectoryName = recapSpreadsheetDirectoryName
    }

    String getHomeDataDirectoryPath() {
        new File(System.getProperty(KEY_SYSTEM_PROPERTY_USER_HOME) +
                System.getProperty(KEY_SYSTEM_PROPERTY_FILE_SEPARATOR) +
                homeDirectoryName).path
    }

    void createHomeDataDirectory() {
        File file = new File(getHomeDataDirectoryPath())
        if (!file.exists()) file.mkdir()
        else if (file.isFile()) {
            file.delete()
            file.mkdir()
        }
    }

    String getRecapSpreadsheetDirectoryPath() {
        new File(getHomeDataDirectoryPath() +
                System.getProperty(KEY_SYSTEM_PROPERTY_FILE_SEPARATOR) +
                recapSpreadsheetDirectoryName).path
    }

    @Override
    Boolean initDataHomeDirectory() {
        File file = new File(getHomeDataDirectoryPath())
        if (!file.exists() ||
                (file.exists() && file.isFile()))
            createHomeDataDirectory()
        file.exists() && file.isDirectory()
    }

    @Override
    Boolean initRecapSpreadsheetDirectory() {
        assert initDataHomeDirectory()
        File file = new File(getRecapSpreadsheetDirectoryPath())
        if (!file.exists() ||
                (file.exists() && file.isFile()))
            createRecapSpreadsheetDirectory()
        assert file.exists()
        assert file.isDirectory()
        file.exists() && file.isDirectory()
    }

    void createRecapSpreadsheetDirectory() {
        File file = new File(getRecapSpreadsheetDirectoryPath())
        if (!file.exists()) file.mkdir()
        else if (file.isFile()) {
            file.delete()
            assert !file.exists
            file.mkdir()
            assert file.exists()
        }
    }

    @Override
    Boolean init() {
        assert initRecapSpreadsheetDirectory()
        File dataHomeDirectoryFile =
                new File(getHomeDataDirectoryPath())
        File recapitulatifSpreadsheetDirectory =
                new File(getRecapSpreadsheetDirectoryPath())
        if ((!dataHomeDirectoryFile.exists() ||
                !dataHomeDirectoryFile.isDirectory()) ||
                (!recapitulatifSpreadsheetDirectory.exists() ||
                        !recapitulatifSpreadsheetDirectory.isDirectory())) {
            throw new RecapSettingInitialisationException()
        }
        true
    }
}
