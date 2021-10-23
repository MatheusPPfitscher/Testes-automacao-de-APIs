package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.tests.booking.requests.payloads.BookingPayloads;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PutBookingRequest extends BaseTest {

    public Response updateOneBookingWithAuthToken(int id, String token){

        BookingPayloads bookingPayloads = new BookingPayloads();

        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie",token)
                .when()
                .body(bookingPayloads.payloadValidBooking().toString())
                .put("booking/"+id);
    }
}
