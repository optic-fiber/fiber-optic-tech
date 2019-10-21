package com.cheroliv.opticfiber.cucumber

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber)
@CucumberOptions(
        plugin = "pretty",
        features = "src/test/features"
)
//TODO
class CucumberIT {
}

