package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class PostBookingRequest {

    @Step("Cria e retorna os dados da reserva criada em caso de sucesso")
    public Response postNewBooking(JSONObject payload){
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(payload.toString())
                .post("booking");
    }

    public Response postMissingDataNewBooking(JSONObject payload){
        payload.remove("lastname");
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(payload.toString())
                .post("booking");
    }

    public Response postExtraDataNewBooking(JSONObject payload){
        payload.put("towel","included");
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(payload.toString())
                .post("booking");
    }

    public Response postWrongHeaderNewBooking(JSONObject payload){
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "GarbageIn/GargabeOut")
                .when()
                .body(payload.toString())
                .post("booking");
    }
}
