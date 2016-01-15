package my.com.test.chm.tests;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber.json"},
//        features = {"chm/src/test/resources/features/node-2-2-1/Login.feature"},
        monochrome = true,
        tags = {"~@skip"})
/**
 * Class for run cucumber features.
 */
public class CukesFeaturesRunner {}
