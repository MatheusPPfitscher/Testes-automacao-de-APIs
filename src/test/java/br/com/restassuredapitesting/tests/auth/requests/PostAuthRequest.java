package br.com.restassuredapitesting.tests.auth.requests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.tests.auth.requests.payload.AuthPayloads;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostAuthRequest {

    @Step("Gera um Auth token da API")
    public Response createAuthToken(){

        AuthPayloads authPayloads = new AuthPayloads();
        return given()
                .header("Content-Type","application/json")
                .when()
                .body(authPayloads.jsonAuthLogin().toString())
                .post("auth");
    }

    @Step("Retorna um Auth token para uso dos testes")
    public String getToken(){
        return "token=" + this.createAuthToken()
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}
