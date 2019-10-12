package com.cheroliv.opticfiber.controller

import com.cheroliv.opticfiber.inter.domain.InterDto
import com.cheroliv.opticfiber.inter.service.InterService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HomePageController {

    final InterService interService

    HomePageController(InterService interService) {
        this.interService = interService
    }

    @GetMapping('/viewresolver')
    String viewResolver() {
        "viewresolver"
    }

    @GetMapping('/inters')
    @ResponseBody
    List<InterDto> inter() {
        interService.getAll()
    }

    @GetMapping('/')
    String homePage() {
        'index'
    }

    @PostMapping('/')
    String submitInter() {
        'inter_confirmation'
    }
}
