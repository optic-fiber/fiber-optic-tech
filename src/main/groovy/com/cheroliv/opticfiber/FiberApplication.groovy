package com.cheroliv.opticfiber

import com.cheroliv.opticfiber.config.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

import static org.springframework.boot.SpringApplication.run

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties)
class FiberApplication {
    static void main(String[] args) {
        run(FiberApplication, args)
    }
}
