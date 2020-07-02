import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"autotests/glue"},
        tags = {"@scenario1,@scenario2,@scenario3,@scenario4,@scenario5,@scenario6,@scenario7,@scenario8,@scenario9"},
        strict = true
)
public class CucumberRunner {
}
