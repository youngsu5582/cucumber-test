package corea.auth.acceptance;

import corea.auth.dto.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MemberAuthStep {
    public static Response 회원가입(final LoginRequest loginRequest) {
        //@formatter:off
        return RestAssured.given().body(loginRequest).contentType(ContentType.JSON)
                .when().post("/login")
                .then().log().all().assertThat().extract().response();
        //@formatter:on
    }
}
