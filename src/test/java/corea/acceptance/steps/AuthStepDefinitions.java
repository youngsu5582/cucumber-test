package corea.acceptance.steps;

import corea.acceptance.CucumberClient;
import corea.auth.dto.GithubUserInfo;
import corea.auth.dto.LoginRequest;
import corea.auth.dto.LoginResponse;
import corea.auth.infrastructure.GithubClient;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AuthStepDefinitions {
    private static final String GITHUB_CODE = "GITHUB_CODE";

    @Autowired
    private CucumberClient cucumberClient;

    @Autowired
    GithubClient githubClient;

    @Given("웹 클라이언트가 적절한 코드를 전달한다.")
    public String 적절한_코드를_생성한다() {
        final String mockCode = "mocking-code";
        final String mockAccessToken = "mocking-access-token";
        when(githubClient.getAccessToken(mockCode)).thenReturn(mockAccessToken);
        when(githubClient.getUserInfo(mockAccessToken)).thenReturn(new GithubUserInfo(
                "corea",
                "조희선",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                "corea@email.com"
        ));
        cucumberClient.addData(GITHUB_CODE, mockCode);
        return mockCode;
    }

    @Given("웹 클라이언트가 부적절한 코드를 전달한다.")
    public String 부적절한_코드를_생성한다() {
        final String mockCode = "mocking-code";
        when(githubClient.getAccessToken(mockCode)).thenThrow(new CoreaException(ExceptionType.GITHUB_AUTHORIZATION_ERROR));
        cucumberClient.addData(GITHUB_CODE, mockCode);
        return mockCode;
    }

    @When("코드를 통해 소셜 로그인을 진행한다.")
    public void 회원가입() {
        final LoginRequest loginRequest = new LoginRequest(cucumberClient.getData(GITHUB_CODE, String.class));
        //@formatter:off
        final Response response = RestAssured.given().body(loginRequest).contentType(ContentType.JSON)
                .when().post("/login")
                .then().assertThat().extract().response();
        //@formatter:on
        cucumberClient.setResponse(response);
    }


    @Then("서비스가 불가능 하다는 상태코드를 받는다.")
    public void 상태코드가_503을_나타낸다() {
        final int statusCode = cucumberClient.getResponse()
                .statusCode();
        assertThat(statusCode).isEqualTo(503);
    }

    @Then("이미 존재한 유저와 조회한 유저의 정보는 동일하다.")
    public void 유저가_동일한지_검증한다() {
        final Member member = cucumberClient.getData("MEMBER", Member.class);
        final GithubUserInfo userInfo = cucumberClient.getResponse()
                .as(LoginResponse.class)
                .userInfo();

        assertThat(member.getUsername()).isEqualTo(userInfo.login());
        assertThat(member.getEmail()).isEqualTo(userInfo.email());
        assertThat(member.getName()).isEqualTo(userInfo.name());
    }
}
