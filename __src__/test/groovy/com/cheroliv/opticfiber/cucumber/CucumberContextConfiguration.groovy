package com.cheroliv.opticfiber.cucumber

import com.cheroliv.opticfiber.FiberApplication
import io.cucumber.java.Before
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = FiberApplication)
class CucumberContextConfiguration {

    @Before
    void setup_cucumber_spring_context() {
//        // Dummy method so cucumber will recognize this class as glue
//        // and use its context configuration.
    }
}
