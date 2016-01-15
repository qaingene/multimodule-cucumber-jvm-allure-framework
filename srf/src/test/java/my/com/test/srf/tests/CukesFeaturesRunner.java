package my.com.test.srf.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber.json"},
//        features = {"srf/src/test/resources/features/Login.feature"},
        monochrome = true,
        tags = {"~@skip"})
/**
 * Class for run cucumber features.
 */
public class CukesFeaturesRunner {}
