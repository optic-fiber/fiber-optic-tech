package com.cheroliv.opticfiber

import com.cheroliv.opticfiber.config.ApplicationProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties)
class FiberApplication {
    static void main(String[] args) {
        SpringApplication.run(FiberApplication, args)
    }
}
