package com.cheroliv.opticfiber.inter.service

import com.cheroliv.opticfiber.inter.domain.InterDto


interface InterDataService {
    InterDto find(String nd, String type)

    Integer countMois()

    List<Map<String, Integer>> findAllMoisFormatFrParAnnee()

    String getJsonBackupFilePath()

    void importJsonFromFile()

    String buildJsonInter(InterDto inter)

    void saveToJsonFile()
}
