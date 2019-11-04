package com.cheroliv.opticfiber.recap.service

import com.cheroliv.opticfiber.domain.Recap
import com.cheroliv.opticfiber.view.SpreadsheetRecap

import java.time.LocalDateTime

//import com.cheroliv.fiber.recap.model.Recap
//import com.cheroliv.fiber.recap.spreadsheet.SpreadsheetRecap

interface RecapService {
//    void setPath(String path)
    SpreadsheetRecap init(LocalDateTime startDate, LocalDateTime endDate)

    SpreadsheetRecap init()

    Recap processRecap(
            String nomFeuilles,
            Integer moisInt,
            Integer anneeInt)

    SpreadsheetRecap processFeuilles()

    SpreadsheetRecap processFeuilles(LocalDateTime startDate, LocalDateTime endDate)

    SpreadsheetRecap processClasseurFeuilles(LocalDateTime startDate, LocalDateTime endDate)

    SpreadsheetRecap processClasseurFeuilles()

    List<String> nomFeuilles()

    String generateRecapFileName(LocalDateTime startDate, LocalDateTime endDate)
}
