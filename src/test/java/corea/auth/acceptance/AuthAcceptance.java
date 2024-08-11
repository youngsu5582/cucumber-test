package corea.auth.acceptance;

import config.AcceptanceTest;
import corea.auth.dto.GithubUserInfo;
import corea.auth.dto.LoginRequest;
import corea.auth.infrastructure.GithubClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static corea.auth.acceptance.MemberAuthStep.회원가입;
import static org.mockito.Mockito.when;

@AcceptanceTest
class AuthAcceptance {
    @MockBean
    GithubClient githubClient = Mockito.spy(GithubClient.class);
    //    Scenario: 깃허브에서 받은 적절한 코드를 통해 유저 조회 API를 사용해서 회원가입을 진행한다.
    @Autowired
    MemberStep memberStep;

    private final String mockCode = "mocking-code";
    private final String mockAccessToken = "mocking-access-token";

    @Test
    void 코드를_기반으로_조회후_유저가_없으면_회원가입을_진행한다() {

        final String username = "corea";
        //    Given 웹 클라이언트가 적절한 코드를 전달한다.
        when(githubClient.getAccessToken(mockCode)).thenReturn(mockAccessToken);
        when(githubClient.getUserInfo(mockAccessToken)).thenReturn(new GithubUserInfo(
                username,
                "조희선",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                "corea@email.com"
        ));

        // And 이미 "corea"라는 유저네임을 가지는 유저가 존재하지 않는다.
        memberStep.유저네임에_해당하는_유저가_있는지_검증(username);


        //When 코드를 통해 소셜 로그인을 진행한다.
        final LoginRequest loginRequest = new LoginRequest(mockCode);
        final Response response = 회원가입(loginRequest);

        //    Then 유저네임이 "corea"인 유저가 조회되어야 한다.

        memberStep.유저네임을_통해_조회(username);
    }
}
