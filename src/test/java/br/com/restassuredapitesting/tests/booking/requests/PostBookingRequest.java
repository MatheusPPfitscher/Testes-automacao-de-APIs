package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.requests.payloads.BookingPayloads;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostBookingRequest {

    @Step("Cria e retorna os dados da reserva criada em caso de sucesso")
    public Response postNewBooking(){
        BookingPayloads bookingPayloads = new BookingPayloads();
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPayloads.payloadValidBooking().toString())
                .post("booking");
    }
}
