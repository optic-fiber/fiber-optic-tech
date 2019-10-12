package com.cheroliv.opticfiber.service

interface SettingService {
    void settingUpApp()
    boolean isAppDataHomeDirectoryExists()
    void createAppDataHomeDirectory()
}