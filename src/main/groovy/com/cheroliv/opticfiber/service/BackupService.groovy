package com.cheroliv.opticfiber.service

interface BackupService {
    Boolean isDataBackupFileExists()

    void loadBackupInDatabase()

    void createDataBackupFile()
}