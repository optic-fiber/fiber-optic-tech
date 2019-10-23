package com.cheroliv.opticfiber.recap.controller

import com.cheroliv.opticfiber.recap.service.RecapService
import com.cheroliv.opticfiber.recap.spreadsheet.SpreadsheetRecap
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDateTime

//TODO recap API
@RestController
class RecapController {
    final RecapService service

    RecapController(RecapService service) {
        this.service = service
    }

    def getRecap() {
    }

    def getRecap(LocalDateTime startDate, LocalDateTime endDate) {
        SpreadsheetRecap spreadsheetRecap = service
                .processClasseurFeuilles(startDate, endDate)
        spreadsheetRecap.classeur
    }


//    @RequestMapping(value = "/download", method = GET)
//    String download(Model model) {
//        model.addAttribute("users", userService.findAllUsers())
//        return ""
//    }
}
