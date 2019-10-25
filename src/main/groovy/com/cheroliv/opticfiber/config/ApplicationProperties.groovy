package com.cheroliv.opticfiber.config


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(
        prefix = 'application',
        ignoreUnknownFields = false)
class ApplicationProperties {

    final Data data = new Data()

    static class Data {
        String homeDirectoryName = ApplicationDefaults
                .Data
                .homeDirectoryName
        String jsonBackupFileName = ApplicationDefaults
                .Data
                .jsonBackupFileName
        String recapSpreadsheetDirectoryName = ApplicationDefaults
                .Data
                .recapSpreadsheetDirectoryName
        String recapSpreadsheetFileName = ApplicationDefaults
                .Data
                .recapSpreadsheetFileName

    }
}

