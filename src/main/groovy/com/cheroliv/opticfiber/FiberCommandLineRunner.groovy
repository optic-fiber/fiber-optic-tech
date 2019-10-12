package com.cheroliv.opticfiber

import groovy.util.logging.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Slf4j
@Component
class FiberCommandLineRunner implements CommandLineRunner {

    ApplicationContext applicationContext

    FiberCommandLineRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext
    }


    @Override
    void run(String... args) throws Exception {
//        log.info "bean provided by spring container : ${applicationContext.getBeanDefinitionNames().toArrayString()}"
    }
}
