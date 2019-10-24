package com.cheroliv.opticfiber.config


import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(
        prefix = 'application',
        ignoreUnknownFields = false)
//@EnableJpaRepositories(
//        basePackages = [
//                'com.cheroliv.core.entity.dao',
//                'com.cheroliv.opticfiber.inter.entity.dao',
//                'com.cheroliv.opticfiber.planning.entity.dao'
//        ],
//        repositoryBaseClass = ExtendedRepositoryImpl)
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

