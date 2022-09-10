package io.componenttesting.testmanager;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CucumberRunnerTest {

    @ClassRule
    public static WireMockRule wireMockRule = new WireMockRule(options().port(8080).notifier(new ConsoleNotifier(true)));

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("metrics.endpoint", String.format("http://localhost:%s/", wireMockRule.port()));
    }
}
