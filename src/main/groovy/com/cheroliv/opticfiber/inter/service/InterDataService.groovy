package com.cheroliv.opticfiber.inter.service

import com.cheroliv.opticfiber.inter.domain.InterDto

import java.time.LocalDateTime


interface InterDataService {
    InterDto find(String nd, String type)

    Integer countMois()

    Integer countMois(
            LocalDateTime startDate,
            LocalDateTime endDate)

    List<Map<String, Integer>> findAllMoisFormatFrParAnnee()

    List<Map<String, Integer>> findAllMoisFormatFrParAnnee(
            LocalDateTime startDate,
            LocalDateTime endDate)

    String getJsonBackupFilePath()

    void importJsonFromFile()

    String buildJsonInter(InterDto inter)

    void saveToJsonFile()
}
