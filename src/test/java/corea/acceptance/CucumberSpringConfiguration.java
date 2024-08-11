package corea.acceptance;

import config.DatabaseClearTestExecutionListener;
import config.ResetMockTestExecutionListener;
import config.RestAssuredExecutionListener;
import corea.auth.infrastructure.GithubClient;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;


@Import({CucumberClient.class})
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
        value = {
                ResetMockTestExecutionListener.class,
                RestAssuredExecutionListener.class,
                DatabaseClearTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public class CucumberSpringConfiguration {

    @MockBean
    private GithubClient githubClient;
}
