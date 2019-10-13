package com.cheroliv.opticfiber.recap.controller


import org.springframework.web.bind.annotation.RestController

import java.time.LocalDateTime

//TODO recap API
@RestController
class RecapController {
    def getRecap() {
    }

    def getRecap(LocalDateTime startDate, LocalDateTime endDate) {

    }


//    @RequestMapping(value = "/download", method = GET)
//    String download(Model model) {
//        model.addAttribute("users", userService.findAllUsers())
//        return ""
//    }
}
