package com.cheroliv.opticfiber.recap.service

import com.cheroliv.opticfiber.recap.model.Recap
import com.cheroliv.opticfiber.recap.spreadsheet.SpreadsheetRecap

import java.time.LocalDateTime

//import com.cheroliv.fiber.recap.model.Recap
//import com.cheroliv.fiber.recap.spreadsheet.SpreadsheetRecap

interface RecapService {
//    void setPath(String path)
    SpreadsheetRecap init()

    Recap processRecap(
            String nomFeuilles,
            Integer moisInt,
            Integer anneeInt)
    SpreadsheetRecap processFeuilles()
    SpreadsheetRecap processClasseurFeuilles(String classeurPath)
    List<String> nomFeuilles()

    String generateRecapFileName(LocalDateTime startDate, LocalDateTime endDate)
}
