package com.cheroliv.opticfiber.controller


import com.cheroliv.opticfiber.domain.InterDto
import com.cheroliv.opticfiber.service.InterService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE

@Controller
class HomePageController {

    final InterService interService

    HomePageController(InterService interService) {
        this.interService = interService
    }

    @GetMapping
    String homePage() {
        'index'
    }

    @GetMapping('/inter_add')
    String addInter(Model model) {
        'add_inter'
    }

    @GetMapping('/inter/{interId}')
    ModelAndView viewInter(@PathVariable Long interId) {
        InterDto interBean = interService
                .findById(interId) ?:
                new InterDto()
        ModelAndView mav = new ModelAndView(
                viewName: 'view_inter')
        mav.addObject('interBean', interBean)
        mav
    }

    @PostMapping
    String submitInter(@ModelAttribute InterFormBean interDto) {
        'confirm_inter'
    }

    @PostMapping('/inter_detail')
    String updateInter(@ModelAttribute InterFormBean interDto) {
        'detail_inter'
    }

    @GetMapping(value = '/inters',
            produces = [APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    List<InterDto> inter() { interService.getAll() }


}
