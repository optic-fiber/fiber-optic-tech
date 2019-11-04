package com.cheroliv.opticfiber

import com.cheroliv.opticfiber.service.RecapService
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Slf4j
@Component
@TypeChecked
class FiberCommandLineRunner implements CommandLineRunner {

    ApplicationContext applicationContext

    FiberCommandLineRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext
    }


    @Override
    void run(String... args) throws Exception {
//        log.info "bean provided by spring container : ${applicationContext.getBeanDefinitionNames().toArrayString()}"
        RecapService recapService = applicationContext.getBean(RecapService)
//        recapService.processClasseurFeuilles()
    }
}
