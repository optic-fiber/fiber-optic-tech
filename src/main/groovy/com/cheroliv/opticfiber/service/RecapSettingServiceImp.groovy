package com.cheroliv.opticfiber.service

import com.cheroliv.opticfiber.service.exceptions.RecapSettingInitialisationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import static com.cheroliv.opticfiber.util.ApplicationUtils.separator
import static com.cheroliv.opticfiber.util.ApplicationUtils.userHomePath

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
        new File(userHomePath +
                separator +
                homeDirectoryName).path
    }

    void createHomeDataDirectory() {
        File file = new File(homeDataDirectoryPath)
        if (!file.exists()) file.mkdir()
        else if (file.file) {
            file.delete()
            file.mkdir()
        }
    }

    String getRecapSpreadsheetDirectoryPath() {
        new File(homeDataDirectoryPath +
                separator +
                recapSpreadsheetDirectoryName).path
    }

    @Override
    Boolean initDataHomeDirectory() {
        File file = new File(homeDataDirectoryPath)
        if (!file.exists() ||
                (file.exists() && file.file))
            createHomeDataDirectory()
        file.exists() && file.directory
    }

    @Override
    Boolean initRecapSpreadsheetDirectory() {
        assert initDataHomeDirectory()
        File file = new File(recapSpreadsheetDirectoryPath)
        if (!file.exists() ||
                (file.exists() && file.file))
            createRecapSpreadsheetDirectory()
        assert file.exists()
        assert file.directory
        file.exists() && file.directory
    }

    void createRecapSpreadsheetDirectory() {
        File file = new File(recapSpreadsheetDirectoryPath)
        if (!file.exists()) file.mkdir()
        else if (file.isFile()) {
            file.delete()
            assert !file.exists()
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
                !dataHomeDirectoryFile.directory) ||
                (!recapitulatifSpreadsheetDirectory.exists() ||
                        !recapitulatifSpreadsheetDirectory.directory)) {
            throw new RecapSettingInitialisationException()
        }
        true
    }
}
