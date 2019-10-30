package com.cheroliv.opticfiber

import com.cheroliv.opticfiber.config.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

import static org.springframework.boot.SpringApplication.run

@SpringBootApplication/*(
        scanBasePackages = [
                'com.cheroliv.core.config',
                'com.cheroliv.core.entity',
                'com.cheroliv.core.entity.dao',
                'com.cheroliv.core.repository',
                'com.cheroliv.core.service',
                'com.cheroliv.opticfiber',
                'com.cheroliv.opticfiber.config',
                'com.cheroliv.opticfiber.controller',
                'com.cheroliv.opticfiber.inter.controller',
                'com.cheroliv.opticfiber.inter.entity',
                'com.cheroliv.opticfiber.inter.entity.dao',
                'com.cheroliv.opticfiber.inter.repository',
                'com.cheroliv.opticfiber.inter.service',
                'com.cheroliv.opticfiber.planning.entity',
                'com.cheroliv.opticfiber.planning.entity.dao',
                'com.cheroliv.opticfiber.planning.repository',
                'com.cheroliv.opticfiber.planning.service',
                'com.cheroliv.opticfiber.recap.controller',
                'com.cheroliv.opticfiber.recap.service',
                'com.cheroliv.opticfiber.service',
                'com.cheroliv.opticfiber.view'
        ])*/
@EnableConfigurationProperties(ApplicationProperties)
class FiberApplication {
    static void main(String[] args) {
        run(FiberApplication, args)
    }
}
