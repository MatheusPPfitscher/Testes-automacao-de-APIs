package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class PutBookingRequest {

    @Step("Atualiza uma Reserva específica usando um Auth token")
    public Response updateOneBookingWithAuthToken(int id, String token, JSONObject payload){

        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie",token)
                .when()
                .body(payload.toString())
                .put("booking/"+id);
    }

    public Response updateOneBookingWithBasicAuth(int id, JSONObject payload){
        return given()
                .header("Content-Type","application/json")
                .header("Accept", "application/json")
                .auth().preemptive().basic("admin","password123")
                .when()
                .body(payload.toString())
                .put("booking/"+id);
    }
}
