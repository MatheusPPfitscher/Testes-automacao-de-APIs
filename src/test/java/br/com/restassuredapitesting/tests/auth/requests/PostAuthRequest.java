package br.com.restassuredapitesting.tests.auth.requests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.tests.auth.requests.payload.AuthPayloads;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostAuthRequest extends BaseTest {

    public Response createAuthToken(){

        AuthPayloads authPayloads = new AuthPayloads();
        return given()
                .header("Content-Type","application/json")
                .when()
                .body(authPayloads.jsonAuthLogin().toString())
                .post("auth");
    }

    public String getToken(){
        return "token=" + this.createAuthToken()
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}
