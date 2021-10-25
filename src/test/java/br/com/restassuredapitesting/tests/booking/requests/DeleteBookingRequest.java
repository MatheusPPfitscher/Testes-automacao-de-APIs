package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteBookingRequest {

    public Response deleteBookingFromIdWithAuthToken(int id, String token){
        return given()
                .pathParam("id",String.valueOf(id))
                .header("Content-Type", "application/json")
                .header("Cookie",token)
                .when()
                .delete("booking/{id}");
    }
}
