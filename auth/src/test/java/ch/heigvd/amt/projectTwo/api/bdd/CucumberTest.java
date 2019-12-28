package ch.heigvd.amt.projectTwo.api.bdd;

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * To run cucumber test.
 */

// Boilerplate code taken from https://github.com/bcarun/cucumber-samples/tree/master/hello-springboot-cucumber

@RunWith(CucumberReportRunner.class)
@CucumberOptions(features = "classpath:features", plugin = {"pretty", "json:target/cucumber-report.json"})
public class CucumberTest {

}
